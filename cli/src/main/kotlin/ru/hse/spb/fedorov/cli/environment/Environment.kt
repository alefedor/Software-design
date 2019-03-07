package ru.hse.spb.fedorov.cli.environment

import ru.hse.spb.fedorov.cli.command.Command
import ru.hse.spb.fedorov.cli.command.CommandResult

/**
 * Abstraction for variables and commands of the command shell
 */
interface Environment {
    /**
     * Defines new command by its name
     */
    fun setCommand(name: String, command: Command)

    /**
     * Runs a command by its name. If there are no such commands, then tries to launch a process in the current directory.
     */
    fun executeCommand(name: String, args: List<String>, input: String): CommandResult

    /**
     * Defines new variable with a specific value
     */
    fun setVariable(name: String, value: String)

    /**
     * Returns the value of the variable defined by its name
     */
    fun getVariable(name: String): String
}