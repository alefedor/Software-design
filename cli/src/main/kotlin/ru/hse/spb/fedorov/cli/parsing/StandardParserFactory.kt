package ru.hse.spb.fedorov.cli.parsing

import ru.hse.spb.fedorov.cli.environment.Environment

object StandardParserFactory {
    fun createParser(environment: Environment): Parser =
        GeneralParser(
            StandardSubstitutor(environment, StandardQuoteHandlerSupplier),
            StandardTokenizer(StandardQuoteHandlerSupplier)
        )
}