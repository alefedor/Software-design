package ru.hse.spb.fedorov.cli.command

import org.junit.Test
import org.junit.Assert.*
import ru.hse.spb.fedorov.cli.environment.MapEnvironment
import ru.hse.spb.fedorov.cli.environment.StandardEnvironmentFactory
import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.nio.file.Paths

class EnvironmentalCommandsTest {
    @Test
    fun testAssignmentCommand() {
        val environment = MapEnvironment()
        assertEquals("", AssigmentCommand.execute(listOf("like", "cats and dogs"), "", environment).output)
        assertEquals("cats and dogs", environment.getVariable("like"))
    }

    @Test(expected = CommandShellException::class)
    fun testAssignmentCommandShellExceptionTooManyArguments() {
        val environment = MapEnvironment()
        assertEquals("", AssigmentCommand.execute(listOf("like", "cats and dogs", "and boys"), "", environment).output)
    }

    @Test(expected = CommandShellException::class)
    fun testAssignmentCommandShellExceptionTooLittleArguments() {
        val environment = MapEnvironment()
        assertEquals("", AssigmentCommand.execute(listOf("like"), "", environment).output)
    }

    @Test
    fun testPwdCommand() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        val pwd = PwdCommand.execute(listOf(), "", environment).output
        assertEquals(Paths.get(".").toAbsolutePath(), Paths.get(pwd + "/.").toAbsolutePath())
    }
}