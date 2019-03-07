package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.exception.CommandShellException

object AssigmentCommand : EnvironmentalCommand() {
    /**
     * Sets the values of the variable denoted by first argument to second argument.
     * @throws CommandShellException when runs with other than two number of arguments
     */
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        if (args.size != 2) throw CommandShellException("Assignment with more that 2 arguments")

        environment.setVariable(args[0], args[1])
        return CommandResult("")
    }
}