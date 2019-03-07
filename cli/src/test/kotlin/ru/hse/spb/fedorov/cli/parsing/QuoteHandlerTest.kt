package ru.hse.spb.fedorov.cli.parsing

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class QuoteHandlerTest {
    private lateinit var quoteHandler: QuoteHandler

    @Before
    fun setUp() {
        quoteHandler = StandardQuoteHandler()
    }

    @Test
    fun testAddSymbol() {
        quoteHandler.addSymbol('c')
        quoteHandler.addSymbol('"')
    }

    @Test
    fun testInQuotes() {
        assertEquals(false, quoteHandler.inQuotes())
        quoteHandler.addSymbol('c')
        assertEquals(false, quoteHandler.inQuotes())
        quoteHandler.addSymbol('"')
        assertEquals(true, quoteHandler.inQuotes())
        quoteHandler.addSymbol('"')
        assertEquals(false, quoteHandler.inQuotes())
    }

    @Test
    fun testInQuotesNestedQuotes() {
        quoteHandler.addSymbol('"')
        assertEquals(true, quoteHandler.inQuotes())
        quoteHandler.addSymbol('\'')
        assertEquals(true, quoteHandler.inQuotes())
        quoteHandler.addSymbol('"')
        assertEquals(false, quoteHandler.inQuotes())
    }

    @Test
    fun testCanSubstitute() {
        quoteHandler.addSymbol('"')
        assertEquals(true, quoteHandler.canSubstitute())
        quoteHandler.addSymbol('\'')
        assertEquals(true, quoteHandler.canSubstitute())
        quoteHandler.addSymbol('"')
        assertEquals(true, quoteHandler.canSubstitute())
        quoteHandler.addSymbol('\'')
        assertEquals(false, quoteHandler.canSubstitute())
        quoteHandler.addSymbol('\'')
        assertEquals(true, quoteHandler.canSubstitute())
    }
}