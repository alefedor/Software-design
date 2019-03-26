package ru.hse.spb.fedorov.cli

import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.exception.CommandShellException
import ru.hse.spb.fedorov.cli.exception.ParserException
import ru.hse.spb.fedorov.cli.executor.StandardCommandExecutor
import ru.hse.spb.fedorov.cli.parsing.StandardParserFactory
import java.io.IOException
import java.lang.IllegalArgumentException
import java.nio.file.InvalidPathException


/**
 * A CommandLine Interface for Environment
 */
class CommandLineInterface(val environment: Environment) {
    val parser = StandardParserFactory.createParser(environment)
    val executor = StandardCommandExecutor(environment)

    /**
     * Runs command line interactions with Environment
     */
    fun run() {
        while (true) {
            val command = readLine() ?: break

            try {
                val commands = parser.parse(command)
                print(executor.execute(commands))
            } catch (e: InvalidPathException) {
                print(e.message)
            } catch (e: ParserException) {
                print(e.message)
            } catch (e: CommandShellException) {
                print(e.message)
            } catch (e: IOException) {
                print(e.message)
            } catch (e: IllegalArgumentException) {
                print(e.message)
            }

            print(System.lineSeparator())
        }
    }
}