package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment

object PwdCommand : EnvironmentalCommand() {
    /**
     * Returns current directory path.
     */
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        return CommandResult(environment.getCurrentWorkingDirectory().toString())
    }
}