package ru.hse.spb.fedorov.cli.exception

import java.lang.Exception

/**
 * Exception thrown because of failure in command shell execution.
 */
class CommandShellException(message: String) : Exception(message)