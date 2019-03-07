package ru.hse.spb.fedorov.cli.parsing

import ru.hse.spb.fedorov.cli.environment.Environment
import java.util.function.Supplier

/**
 * An implementation of a substitutor for the command shell
 */
class StandardSubstitutor(
    val environment: Environment,
    private val quoteHandlerSupplier: Supplier<QuoteHandler>
) : Substitutor {
    /**
     * Substitute values of variables defined by $ into a string that are not into string quotes.
     */
    override fun substitute(input: String): String {
        val quoteHandler = quoteHandlerSupplier.get()
        val variableNameBuilder = StringBuilder()
        val resultBuilder = StringBuilder()
        var isVariableName = false

        for (c in input) {
            quoteHandler.addSymbol(c)

            if (isVariableName) {
                if (!c.isLetterOrDigit()) {
                    isVariableName = false
                    substituteVariable(resultBuilder, variableNameBuilder)
                } else { // name can consist only of letters and digits
                    variableNameBuilder.append(c)
                }
            }

            if (quoteHandler.canSubstitute() && c == Substitutor.VARIABLE_MARKER)
                isVariableName = true

            if (!isVariableName)
                resultBuilder.append(c)
        }

        if (isVariableName) // the last variable name in not ended
            substituteVariable(resultBuilder, variableNameBuilder)

        return resultBuilder.toString()
    }

    private fun substituteVariable(resultBuilder: StringBuilder, variableNameBuilder: StringBuilder) {
        val variableName = variableNameBuilder.toString()

        if (variableName.isEmpty()) {
            resultBuilder.append(Substitutor.VARIABLE_MARKER)
            return
        }

        resultBuilder.append(environment.getVariable(variableName))
        variableNameBuilder.clear()
    }
}