package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment
import java.nio.file.Paths

object PwdCommand : EnvironmentalCommand() {
    /**
     * Returns current directory path.
     */
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        return CommandResult(Paths.get("").toAbsolutePath().toString())
    }
}