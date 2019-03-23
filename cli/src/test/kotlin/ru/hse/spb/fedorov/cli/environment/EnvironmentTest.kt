package ru.hse.spb.fedorov.cli.environment

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.hse.spb.fedorov.cli.command.EchoCommand

class EnvironmentTest {
    private lateinit var environment: Environment

    @Before
    fun setUp() {
        environment = MapEnvironment()
    }

    @Test
    fun testSetVariable() {
        environment.setVariable("fate", "zero")
    }

    @Test
    fun testSetVariableValue() {
        environment.setVariable("fate", "zero")
        assertEquals("zero", environment.getVariable("fate"))
    }

    @Test
    fun testGetUndefinedVariable() {
        assertEquals("", environment.getVariable("fate"))
    }

    @Test
    fun testReSetVariable() {
        environment.setVariable("fate", "zero")
        environment.setVariable("fate", "extra")
        assertEquals("extra", environment.getVariable("fate"))
    }

    @Test
    fun testDefinedCommand() {
        environment.setCommand("echo", EchoCommand)
        assertEquals("nya", environment.executeCommand("echo", listOf("nya"), "").output)
    }
}