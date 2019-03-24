package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment

/**
 * Changes current directory. Without arguments the directory becomes user's home directory.
 */
object CdCommand : EnvironmentalCommand() {
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        if (args.isNotEmpty()) {
            val directory = getAbsoluteOrRelativeDirectory(args[0], environment)
            environment.setVariable(Environment.CURRENT_DIRECTORY_PATH, directory.canonicalPath)
        } else {
            environment.setVariable(Environment.CURRENT_DIRECTORY_PATH, System.getProperty("user.home"))
        }
        return CommandResult("")
    }
}