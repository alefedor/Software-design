package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.exception.CommandShellException

/**
 * Changes current directory. Without arguments the directory becomes user's home directory.
 */
object CdCommand : EnvironmentalCommand() {
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        if (args.size > 1) throw CommandShellException("cd: too many arguments")

        val pathDelta = if (args.size == 1) args[0] else System.getProperty("user.home")
        val newPath = environment.getCurrentWorkingDirectory().resolve(pathDelta).normalize()
        if (newPath.toFile().isDirectory) {
            environment.setCurrentWorkingDirectory(newPath)
        } else {
            throw CommandShellException("cd: no such file or directory: ${args[0]}")
        }

        return CommandResult("")
    }
}