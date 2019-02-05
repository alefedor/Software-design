package ru.hse.spb.fedorov.cli.parsing

/**
 * An implementation of Parser
 */
class GeneralParser(val substitutor: Substitutor, val tokenizer: Tokenizer) : Parser {
    /**
     * @inheritDoc
     */
    override fun parse(input: String): List<CommandArguments> {
        val text = substitutor.substitute(input)
        val tokens = tokenizer.tokenize(text)
        val currentCommandArguments: MutableList<String> = mutableListOf()
        val parsedCommands: MutableList<CommandArguments> = mutableListOf()
        for (token in tokens) {
            val isPipeToken = token.length == 1 && token[0] == Parser.PIPE
            if (isPipeToken)
                addParsedCommand(parsedCommands, currentCommandArguments)
            else
                currentCommandArguments.add(token)
        }

        addParsedCommand(parsedCommands, currentCommandArguments)

        val resultCommands: MutableList<CommandArguments> = mutableListOf()

        for (command in parsedCommands) {
            val hasValidAssignment = command.args[0].count({ it == Parser.ASSIGNMENT }) == 1 && command.args[0][0] != Parser.ASSIGNMENT
            if  (hasValidAssignment) {
                val commandArguments = command.args.toMutableList()
                val assignmentParts = commandArguments[0].split(Parser.ASSIGNMENT)
                commandArguments.removeAt(0)
                commandArguments.add(0, "=")
                commandArguments.add(1, assignmentParts[0])
                commandArguments.add(2, if (assignmentParts.size == 2) assignmentParts[1] else "")

                resultCommands.add(CommandArguments(commandArguments))
            } else {
                resultCommands.add(command)
            }
        }

        return resultCommands
    }

    private fun addParsedCommand(parsedCommands: MutableList<CommandArguments>, currentCommandArguments: MutableList<String>) {
        if (currentCommandArguments.isEmpty()) return
        parsedCommands.add(CommandArguments(currentCommandArguments.toList()))
        currentCommandArguments.clear()
    }
}