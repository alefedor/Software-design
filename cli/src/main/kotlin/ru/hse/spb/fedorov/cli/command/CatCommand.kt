package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment

object CatCommand : EnvironmentalCommand() {
    /**
     * If there is no arguments returns input.
     * If there are arguments then returns joined contents of all files denotef by arguments.
     */
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        if (args.isEmpty()) return CommandResult(input)

        return CommandResult(args.joinToString("", "", System.lineSeparator()) {
            environment.getCurrentWorkingDirectory().resolve(it).toFile().readLines()
                .joinToString(System.lineSeparator())
        })
    }
}