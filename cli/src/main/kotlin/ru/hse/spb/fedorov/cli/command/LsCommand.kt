package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment

/**
 * List files in a specified directory. Without tests current directory is listed.
 */
object LsCommand : EnvironmentalCommand() {
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        val directory = getAbsoluteOrRelativeDirectory(if (args.isNotEmpty()) args[0] else "", environment)
        return CommandResult(directory.list().joinToString(" "))
    }
}