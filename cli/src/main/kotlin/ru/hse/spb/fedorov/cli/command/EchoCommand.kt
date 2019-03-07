package ru.hse.spb.fedorov.cli.command

object EchoCommand : GeneralCommand() {
    /**
     * Returns list of arguments separated by a space
     */
    override fun execute(args: List<String>, input: String): CommandResult {
        return CommandResult(args.joinToString(" "))
    }
}