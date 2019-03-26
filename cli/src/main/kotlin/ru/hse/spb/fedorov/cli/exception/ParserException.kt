package ru.hse.spb.fedorov.cli.exception

import java.lang.Exception

/**
 * Exception throw when failed to parse the given command.
 */
class ParserException: RuntimeException {
    constructor(message: String): super(message) {}
    constructor(message: String, cause: Throwable): super(message, cause) {}
}