package ru.hse.spb.fedorov.cli

import ru.hse.spb.fedorov.cli.environment.StandardEnvironmentFactory

fun main() {
    val environment = StandardEnvironmentFactory.createEnvironment()

    CommandLineInterface(environment).run()
}
