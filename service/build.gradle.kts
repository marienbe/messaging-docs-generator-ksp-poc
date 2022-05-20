plugins {
    id("com.google.devtools.ksp")
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":mini-domain-event-bus"))
    ksp(project(":processor"))
}
