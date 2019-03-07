package ru.hse.spb.fedorov.cli.parsing

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import ru.hse.spb.fedorov.cli.environment.Environment
import ru.hse.spb.fedorov.cli.environment.MapEnvironment

class SubstitutorTest {
    private lateinit var environment: Environment
    private lateinit var substitutor: Substitutor

    @Before
    fun setUp() {
        environment = MapEnvironment()
        substitutor = StandardSubstitutor(environment, StandardQuoteHandlerSupplier)
    }

    @Test
    fun testSubstituteNothing() {
        assertEquals("what 'does' this mean?", substitutor.substitute("what 'does' this mean?"))
    }

    @Test
    fun testSubstitute() {
        environment.setVariable("this", "that")
        assertEquals("what 'does' that mean?", substitutor.substitute("what 'does' \$this mean?"))
    }

    @Test
    fun testSubstituteInWeakQuotes() {
        environment.setVariable("does", "did")
        assertEquals("what \"did\" this mean?", substitutor.substitute("what \"\$does\" this mean?"))
    }

    @Test
    fun testSubstituteInStrongQuotes() {
        environment.setVariable("does", "did")
        assertEquals("what '\$does' this mean?", substitutor.substitute("what '\$does' this mean?"))
    }
}