package ru.hse.spb.fedorov.cli

import ru.hse.spb.fedorov.cli.environment.StandardEnvironmentFactory

fun main(args: Array<String>) {
    val environment = StandardEnvironmentFactory.createEnvironment()

    CommandLineInterface(environment).run()
}
