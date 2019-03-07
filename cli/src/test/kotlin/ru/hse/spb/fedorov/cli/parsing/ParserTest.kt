package ru.hse.spb.fedorov.cli.parsing

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.environment.StandardEnvironmentFactory
import ru.hse.spb.fedorov.cli.exception.ParserException

class ParserTest {
    private lateinit var parser: Parser
    private lateinit var environment: Environment

    @Before
    fun setUp() {
        environment = StandardEnvironmentFactory.createEnvironment()
        parser = StandardParserFactory.createParser(environment)
    }

    @Test
    fun testIntegration() {
        environment.setVariable("fear", "love")
        assertEquals(
            listOf(CommandArguments(listOf("full of love", "and", "\$undefined"))),
            parser.parse("\"full of \$fear\" and '\$undefined'")
        )
    }

    @Test
    fun testAssignment() {
        assertEquals(listOf(CommandArguments(listOf("=", "ab", "ba"))), parser.parse("ab=ba"))
    }

    @Test
    fun testPipe() {
        assertEquals(listOf(CommandArguments(listOf("echo")), CommandArguments(listOf("echo"))), parser.parse("echo| echo"))
    }

    @Test(expected = ParserException::class)
    fun testStrongQuoteParserException() {
        parser.parse("\'dsf")
    }

    @Test(expected = ParserException::class)
    fun testWeakQuoteParserException() {
        parser.parse("dsf\"")
    }
}