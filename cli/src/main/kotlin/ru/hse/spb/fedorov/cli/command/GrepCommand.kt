package ru.hse.spb.fedorov.cli.command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import ru.hse.spb.fedorov.cli.exception.ParserException
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

object GrepCommand : GeneralCommand() {
    /**
     * Prints all lines corresponding to the regex.
     * Use "-i" for case insensitive search.
     * "-w" to search only for whole words corresponding to the regex.
     * "-A n" to print n lines after found line.
     */
    override fun execute(args: List<String>, input: String): CommandResult {
        lateinit var arguments: GrepArguments
        try {
            arguments = ArgParser(args.toTypedArray()).parseInto(::GrepArguments)
        } catch (e: Exception) {
            throw ParserException("Error in parsing grep arguments: " + e.message)
        }

        val resultBuilder = StringBuilder()

        arguments.run {
            val pattern = if (onlyWholeWord) "(^|\\b)$pattern($|\\b)" else pattern
            val regexOptions = mutableSetOf<RegexOption>()

            if (caseInsensitive)
                regexOptions.add(RegexOption.IGNORE_CASE)

            val regex = pattern.toRegex(regexOptions)

            val lines = if (files.isEmpty()) input.split('\n') else File(files[0]).readLines()

            var shouldPrint = 0

            for (line in lines) {
                if (regex.containsMatchIn(line)) {
                    shouldPrint = afterFoundNumber
                    resultBuilder.append(line)
                    resultBuilder.append('\n')
                } else if (shouldPrint > 0) {
                    shouldPrint--
                    resultBuilder.append(line)
                    resultBuilder.append('\n')
                }
            }
        }

        return CommandResult(resultBuilder.toString())
    }

    private class GrepArguments(parser: ArgParser) {
        val caseInsensitive by parser.flagging(
            "-i",
            help = "search case insensitive"
        )

        val onlyWholeWord by parser.flagging(
            "-w",
            help = "search only whole words"
        )

        val afterFoundNumber by parser.storing(
            "-A",
            help = "print n lines after found match line"
        ) {
            val result = toInt()
            if (result < 0)
                throw IllegalArgumentException("number of lines should be non-negative")
            result
        }.default(0)

        val pattern by parser.positional(
            "PATTERN",
            "pattern for searching"
        )

        val files: List<String> by parser.positionalList(
            "FILES",
            help = "files to search in"
        ).default(emptyList()).addValidator {
            if (files.size > 1)
                throw IllegalArgumentException("should be only one or zero files")
        }
    }
}