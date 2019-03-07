package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.nio.file.Paths

object CatCommand : GeneralCommand() {
    /**
     * If there is no arguments returns input.
     * If there are arguments then returns joined contents of all files denotef by arguments.
     */
    override fun execute(args: List<String>, input: String): CommandResult {
        if (args.isEmpty()) return CommandResult(input)

        return CommandResult(args.joinToString("", "", System.lineSeparator()) {
            Paths.get(it).toFile().readLines().joinToString(System.lineSeparator())
        })
    }
}