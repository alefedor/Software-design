package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment

/**
 * An abstraction for the result of a command execution
 */
data class CommandResult(val output: String)

/**
 * An abstraction for a command
 */
sealed class Command

/**
 * An abstraction for a command that uses Environment
 */
abstract class EnvironmentalCommand : Command() {
    abstract fun execute(args: List<String>, input: String, environment: Environment): CommandResult
}

/**
 * An abstraction for a command that has a general interface
 */
abstract class GeneralCommand : Command() {
    abstract fun execute(args: List<String>, input: String): CommandResult
}

fun Command.executeWithEnvironment(args: List<String>, input: String, environment: Environment): CommandResult = when (this) {
        is EnvironmentalCommand -> execute(args, input, environment)
        is GeneralCommand -> execute(args, input)
    }
