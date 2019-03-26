package ru.hse.spb.fedorov.cli.command

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.ExpectedSystemExit
import ru.hse.spb.fedorov.cli.exception.ParserException


class GeneralCommandsTest {
    @Rule @JvmField
    val exit = ExpectedSystemExit.none()

    @Test
    fun testEchoOneArgument() {
        assertEquals("surprise", EchoCommand.execute(listOf("surprise"), "").output)
    }

    @Test
    fun testEchoSeveralArguments() {
        assertEquals("a ba caba", EchoCommand.execute(listOf("a", "ba", "caba"), "").output)
    }

    @Test
    fun testEchoZeroArguments() {
        assertEquals("", EchoCommand.execute(listOf(), "wow").output)
    }


    @Test
    fun testExit() {
        exit.expectSystemExitWithStatus(0)
        ExitCommand.execute(listOf(), "")
    }

    @Test
    fun testGrepSimpleOneLine() {
        val input = "aba"
        assertEquals("aba\n", GrepCommand.execute(listOf("aba"), input).output)
    }

    @Test
    fun testGrepMultipleLines() {
        val input = listOf("aba", "caba", "rr aba", "trash").joinToString("\n")
        assertEquals("aba\ncaba\nrr aba\n", GrepCommand.execute(listOf("aba"), input).output)
    }

    @Test(expected = ParserException::class)
    fun testGrepParserException() {
        val input = "aba"
        assertEquals("aba\n", GrepCommand.execute(listOf("aba", "-u", "-p"), input).output)
    }

    @Test
    fun testGrepCaseInsensitive() {
        val input = listOf("aba", "caBa", "rr ABA", "trash").joinToString("\n")
        assertEquals("aba\ncaBa\nrr ABA\n", GrepCommand.execute(listOf("aba", "-i"), input).output)
    }

    @Test
    fun testGrepWholeWords() {
        val input = listOf("aba", "caba", "rr aba", "trash").joinToString("\n")
        assertEquals("aba\nrr aba\n", GrepCommand.execute(listOf("aba", "-w"), input).output)
    }

    @Test
    fun testGrepNLines() {
        val input = listOf("aba", "caba", "rr aba", "trash", "some more").joinToString("\n")
        assertEquals("aba\ncaba\nrr aba\ntrash\n", GrepCommand.execute(listOf("aba", "-A", "1"), input).output)
    }

    @Test(expected = ParserException::class)
    fun testGrepParserExceptionNLines() {
        val input = "aba"
        assertEquals("aba\n", GrepCommand.execute(listOf("aba", "-A", "dsf"), input).output)
    }

    @Test
    fun testGrepComplexRegex() {
        val input = listOf("abba", "cabba", "rr abb", "trash", "aabb").joinToString("\n")
        assertEquals("abba\nrr abb\naabb\n", GrepCommand.execute(listOf("(a)+bb(a)*", "-w"), input).output)
    }
}