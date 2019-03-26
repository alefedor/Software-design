package ru.hse.spb.fedorov.cli.environment

import ru.hse.spb.fedorov.cli.command.Command
import ru.hse.spb.fedorov.cli.command.CommandResult
import ru.hse.spb.fedorov.cli.command.executeWithEnvironment
import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths

/**
 * An implementation of Environment using standard map.
 */
class MapEnvironment : Environment {
    private val variables: MutableMap<String, String> = mutableMapOf()
    private val commands: MutableMap<String, Command> = mutableMapOf()

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
        val process = Runtime.getRuntime().exec(name + " " + args.joinToString(" "))

        process.outputStream.write(input.toByteArray())
        process.waitFor()

        if (process.exitValue() != 0)
            throw CommandShellException("Non defined command run, but failed")

        return CommandResult(process.inputStream.readBytes().toString(Charset.defaultCharset()))
    }

    private fun getRelativeDirectory(delta: String): File {
        if (delta.isEmpty()) return File(getVariable(CURRENT_DIRECTORY_PATH))

        val path = getVariable(CURRENT_DIRECTORY_PATH) + File.separator + delta
        val directory = File(path)
        if (!directory.isDirectory) {
            throw CommandShellException("No such directory: $path")
        }
        return directory
    }
}