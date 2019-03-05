package ru.hse.spb.fedorov.cli.parsing

import ru.hse.spb.fedorov.cli.exception.ParserException
import java.lang.StringBuilder
import java.util.function.Supplier

/**
 * A tokenizer for the command shell
 */
class StandardTokenizer(val quoteHandlerSupplier: Supplier<QuoteHandler>) : Tokenizer {
    /**
     * Splits a string with spaces, pipes taking quotes into consideration
     */
    override fun tokenize(input: String): List<String> {
        val tokenBuilder = StringBuilder()
        val quoteHandler = quoteHandlerSupplier.get()
        val tokens: MutableList<String> = mutableListOf()

        for (c in input) {
            val wasInQuotes = quoteHandler.inQuotes()
            quoteHandler.addSymbol(c)
            val inQuotes = quoteHandler.inQuotes()

            if (wasInQuotes != inQuotes) continue; // border quotes are removed

            if (inQuotes) {
                tokenBuilder.append(c)
            } else {
                if (c.isWhitespace())
                    finalizeToken(tokens, tokenBuilder)
                else {
                    if (c == Parser.PIPE) {
                        finalizeToken(tokens, tokenBuilder)
                        tokenBuilder.append(c)
                        finalizeToken(tokens, tokenBuilder)
                    } else {
                        tokenBuilder.append(c)
                    }
                }
            }
        }

        finalizeToken(tokens, tokenBuilder)

        if (quoteHandler.inQuotes()) throw ParserException("Not all quotes are closed")

        return tokens
    }

    private fun finalizeToken(tokens: MutableList<String>, tokenBuilder: StringBuilder) {
        val token = tokenBuilder.toString()
        if (token.isEmpty()) return
        tokens.add(token)
        tokenBuilder.clear()
    }
}