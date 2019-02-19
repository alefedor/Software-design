package ru.hse.spb.fedorov.cli.command

import org.junit.Assert.*
import org.junit.Test
import ru.hse.spb.fedorov.cli.environment.MapEnvironment
import ru.hse.spb.fedorov.cli.environment.StandardEnvironmentFactory
import ru.hse.spb.fedorov.cli.exception.CommandShellException
import java.io.File
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

    @Test
    fun testCdCommandPreviousDirectory() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        val cur_path = Paths.get(".").toRealPath().toString()
        CdCommand.execute(listOf(".."), "", environment)
        assertEquals(
            cur_path.dropLastWhile { it != File.separatorChar }.dropLast(1),
            PwdCommand.execute(listOf(), "", environment).output
        )
    }

    @Test
    fun testCdCommandNextDir() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        val nextDir = "newDir"
        File(nextDir).mkdirs()
        CdCommand.execute(listOf("newDir"), "", environment)
        assertTrue(PwdCommand.execute(listOf(), "", environment).output.endsWith(nextDir))
        File(nextDir).delete()
    }

    @Test(expected = CommandShellException::class)
    fun testCdUnexistingDir() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        CdCommand.execute(listOf("unexistingDir"), "", environment)
    }

    @Test
    fun testLs() {
        val environment = StandardEnvironmentFactory.createEnvironment()
        val files = File(".").list()
        assertArrayEquals(
            files,
            LsCommand.execute(listOf<String>(), "", environment).output.split(" ").toTypedArray()
        )
    }
}