package ru.hse.spb.fedorov.cli.executor

import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.parsing.CommandArguments

class StandardCommandExecutor(val environment: Environment) : CommandExecutor {
    override fun execute(command: CommandArguments): String = execute(listOf(command))

    override fun execute(commands: List<CommandArguments>): String {
        var input = ""

        for (command in commands) {
            input = executeInEnvironment(command, input)
        }

        return input
    }

    private fun executeInEnvironment(command: CommandArguments, input: String) =
        environment.executeCommand(
            command.args[0],
            command.args.subList(1, command.args.size),
            input
        ).output
}