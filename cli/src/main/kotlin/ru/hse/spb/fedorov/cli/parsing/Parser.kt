package ru.hse.spb.fedorov.cli.parsing

/**
 * A abstraction for a command shell parser.
 */
interface Parser {
    companion object {
        const val PIPE = '|'
        const val ASSIGNMENT = '='
    }

    /**
     * Parses an input to retrieve a sequence of commands and their arguments
     */
    fun parse(input: String): List<CommandArguments>
}