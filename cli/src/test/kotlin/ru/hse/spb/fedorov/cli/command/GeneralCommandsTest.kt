package ru.hse.spb.fedorov.cli.command

import org.junit.Assert.*
import org.junit.Test
import org.junit.Rule
import org.junit.contrib.java.lang.system.ExpectedSystemExit


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
    fun testCatZeroArguments() {
        assertEquals("wow", CatCommand.execute(listOf(), "wow").output)
    }

    @Test
    fun testCatArguments() {
        assertEquals("meow", CatCommand.execute(listOf("./src/test/resources/meow"), "wow").output)
    }

    @Test
    fun testExit() {
        exit.expectSystemExitWithStatus(0)
        ExitCommand.execute(listOf(), "")
    }

    @Test
    fun testWc() {
        assertEquals("3 4 27", WcCommand.execute(listOf("./src/test/resources/echo.sh"), "").output)
    }
}