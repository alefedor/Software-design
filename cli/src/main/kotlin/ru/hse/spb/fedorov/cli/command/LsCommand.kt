package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.exception.CommandShellException

/**
 * List files in a specified directory. Without arguments current directory is listed.
 */
object LsCommand : EnvironmentalCommand() {
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        val newPath =
            environment.getCurrentWorkingDirectory().resolve(if (args.isNotEmpty()) args[0] else "").toAbsolutePath()
        val listedDir = newPath.toFile()
        if (!listedDir.isDirectory) throw CommandShellException("ls: no such directory ${args[0]}")
        return CommandResult(newPath.toFile().list().joinToString(" "))
    }
}