package ru.hse.spb.fedorov.cli.exception

import java.lang.Exception

/**
 * Exception throw when failed to parse the given command.
 */
class ParserException(message: String) : Exception(message)