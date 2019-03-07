package ru.hse.spb.fedorov.cli.environment

interface EnvironmentFactory {
    fun createEnvironment(): Environment
}