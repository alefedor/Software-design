package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.nio.file.Paths

object CatCommand : GeneralCommand() {
    override fun execute(args: List<String>, input: String): CommandResult {
        if (args.isEmpty()) return CommandResult(input)

        return CommandResult(args.joinToString("\n") { Paths.get(it).toFile().readLines().joinToString("\n") })
    }
}