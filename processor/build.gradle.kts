import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kspVersion: String by project

plugins {
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
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
}
