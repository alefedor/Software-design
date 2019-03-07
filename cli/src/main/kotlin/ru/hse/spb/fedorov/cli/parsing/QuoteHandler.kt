package ru.hse.spb.fedorov.cli.parsing

/**
 * An abstraction of a quote handler for the command shell
 */
interface QuoteHandler {
    /**
     * Returns whether current position in the string is surrounded with paired quotes.
     */
    fun inQuotes(): Boolean

    /**
     * Parses a new symbol of the input.
     */
    fun addSymbol(c: Char)

    /**
     * Return whether current position in the string is not surrounded by strong quotes.
     */
    fun canSubstitute(): Boolean
}