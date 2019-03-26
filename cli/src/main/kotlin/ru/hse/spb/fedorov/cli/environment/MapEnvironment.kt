package ru.hse.spb.fedorov.cli.environment

import ru.hse.spb.fedorov.cli.command.Command
import ru.hse.spb.fedorov.cli.command.CommandResult
import ru.hse.spb.fedorov.cli.command.executeWithEnvironment
import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths

/**
 * An implementation of Environment using standard map.
 */
class MapEnvironment : Environment {
    private val variables: MutableMap<String, String> = mutableMapOf()
    private val commands: MutableMap<String, Command> = mutableMapOf()

    private var cwd = Paths.get(System.getProperty("user.dir"))
    /**
     * @inheritDoc
     */
    override fun setCommand(name: String, command: Command) {
        commands[name] = command
    }

    /**
     * @inheritDoc
     */
    override fun executeCommand(name: String, args: List<String>, input: String): CommandResult {
        return commands[name]?.executeWithEnvironment(args, input, this) ?: executeNonDefinedCommand(name, args, input)
    }

    /**
     * @inheritDoc
     */
    override fun setVariable(name: String, value: String) {
        variables[name] = value
    }

    /**
     * @inheritDoc
     */
    override fun getVariable(name: String): String = variables.getOrDefault(name, "")

    private fun executeNonDefinedCommand(name: String, args: List<String>, input: String): CommandResult {
        val process = Runtime.getRuntime().exec(name + " " + args.joinToString(" "), null, cwd.toFile())

        process.outputStream.write(input.toByteArray())
        process.waitFor()

        if (process.exitValue() != 0)
            throw CommandShellException("Non defined command run, but failed")

        return CommandResult(process.inputStream.readBytes().toString(Charset.defaultCharset()))
    }

    /**
     * @inheritDoc
     */
    override fun getCurrentWorkingDirectory(): Path = cwd

    /**
     * @inheritDoc
     */
    override fun setCurrentWorkingDirectory(path: Path) {
        cwd = path
    }
}