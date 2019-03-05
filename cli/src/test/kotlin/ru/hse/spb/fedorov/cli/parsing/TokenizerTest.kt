package ru.hse.spb.fedorov.cli.parsing

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class TokenizerTest {
    private lateinit var tokenizer: Tokenizer

    @Before
    fun setUp() {
        tokenizer = StandardTokenizer(StandardQuoteHandlerSupplier)
    }

    @Test
    fun tokenizeOneWord() {
        assertEquals(listOf("OneWord"), tokenizer.tokenize("OneWord"))
    }

    @Test
    fun tokenizeSeveralWords() {
        assertEquals(listOf("Several", "words"), tokenizer.tokenize("Several words"))
    }

    @Test
    fun tokenizeManySpaces() {
        assertEquals(listOf("Several", "words"), tokenizer.tokenize("  Several    words  "))
    }

    @Test
    fun tokenizeInQuotes() {
        assertEquals(listOf("Several  words"), tokenizer.tokenize("\"Several  words\""))
    }

    @Test
    fun tokenizePipe() {
        assertEquals(listOf("Several", "|", "words"), tokenizer.tokenize("Several | words"))
        assertEquals(listOf("Several", "|", "words"), tokenizer.tokenize("Several|words"))
    }
}