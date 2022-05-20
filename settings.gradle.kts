pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("com.google.devtools.ksp") version kspVersion
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "messaging-docs-generator-ksp-poc"
include("processor")
include("mini-domain-event-bus")
include("service")
