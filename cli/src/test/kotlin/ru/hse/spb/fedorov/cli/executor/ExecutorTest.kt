package ru.hse.spb.fedorov.cli.executor

import org.junit.Test
import org.junit.Assert.*
import ru.hse.spb.fedorov.cli.environment.StandardEnvironmentFactory
import ru.hse.spb.fedorov.cli.parsing.CommandArguments

class ExecutorTest {
    @Test
    fun testSingleCommand() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        val executor = StandardCommandExecutor(environment)
        assertEquals("welcome to our land", executor.execute(CommandArguments(listOf("echo", "welcome", "to", "our", "land"))))
    }

    @Test
    fun testCommandChain() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        val executor = StandardCommandExecutor(environment)
        assertEquals("1 4 19", executor.execute(listOf(
            CommandArguments(listOf("echo", "welcome", "to", "our", "land")),
            CommandArguments(listOf("wc"))
        )))
    }
}