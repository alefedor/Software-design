package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.io.File

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

    fun getAbsoluteOrRelativeDirectory(path: String, environment: Environment): File {
        if (path.startsWith(File.separator)) return getAbsoluteDirectory(path)
        if (path.isEmpty()) return File(environment.getVariable(Environment.CURRENT_DIRECTORY_PATH))

        val newPath = environment.getVariable(Environment.CURRENT_DIRECTORY_PATH) + File.separator + path
        return getAbsoluteDirectory(newPath)
    }

    private fun getAbsoluteDirectory(path: String): File {
        val directory = File(path)
        if (!directory.isDirectory) {
            throw CommandShellException("No such directory: $path")
        }
        return directory
    }
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
