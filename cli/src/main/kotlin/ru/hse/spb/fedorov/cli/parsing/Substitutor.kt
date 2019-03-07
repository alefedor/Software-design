package ru.hse.spb.fedorov.cli.parsing

/**
 * An abstraction of substitutor of variables
 */
interface Substitutor {
    companion object {
        const val VARIABLE_MARKER = '$'
    }

    /**
     * Substitute values of variables into a string
     */
    fun substitute(input: String): String
}