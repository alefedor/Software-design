package ru.hse.spb.fedorov.cli.executor

import ru.hse.spb.fedorov.cli.parsing.CommandArguments

/**
 * An abstraction for execution of commands denoted by lists of arguments.
 */
interface CommandExecutor {
    /**
     * Execute a chain of commands separated by pipes.
     */
    fun execute(commands: List<CommandArguments>): String

    /**
     * Execute one command.
     */
    fun execute(command: CommandArguments): String
}