package ru.hse.spb.fedorov.cli.command

import ru.hse.spb.fedorov.cli.environment.Environment
import java.nio.charset.Charset

object WcCommand : EnvironmentalCommand() {
    /**
     * Calculates number of lines, words and bytes in input (if there are no arguments) or in
     * files denoted by arguments. Format: "$lines $words $bytes"
     */
    override fun execute(args: List<String>, input: String, environment: Environment): CommandResult {
        if (args.isEmpty()) return CommandResult(calculateStatisticsFromText(input))

        return CommandResult(args.joinToString("\n") { calculateStatisticsFromFile(it, environment) })
    }

    private fun calculateStatisticsFromFile(fileName: String, environment: Environment): String {
        val bytes = environment.getCurrentWorkingDirectory().resolve(fileName).toFile().readBytes()
        val text = bytes.toString(Charset.defaultCharset())

        return calculateStatisticsFromText(text)
    }

    private fun calculateStatisticsFromText(text: String): String {
        val wordsNumber = text.split("\\s".toRegex()).filter { it.isNotEmpty() }.size
        return "${1 + text.count({ it == '\n' })} ${wordsNumber} ${text.length}"
    }
}