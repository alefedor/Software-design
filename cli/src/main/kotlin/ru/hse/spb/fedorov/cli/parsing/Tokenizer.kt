package ru.hse.spb.fedorov.cli.parsing

/**
 * Interface for ab abstract tokenizer
 */
interface Tokenizer {
    /**
     * Splits a string into substrings
     */
    fun tokenize(input: String): List<String>
}