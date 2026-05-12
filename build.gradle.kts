import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    base
    kotlin("jvm") version "2.3.0"
    application
    id("com.gradleup.shadow") version "9.4.1"
}

application {
    mainClass = "com.anazzubair.gradle.MainKt"
}

val buildDescriptions by tasks.registering(Copy::class) {
    description = "Copies descriptions to build directory. Replaces tokens."
    group = "descriptions"
    dependsOn(tasks.clean)
    from("descriptions") {
        include("*.txt")
    }
    into(layout.buildDirectory.dir("descriptions"))
    filter<ReplaceTokens>(mapOf("tokens" to mapOf("description" to "I am learning Gradle")))
}

val packageDescriptions by tasks.registering(Zip::class) {
    description = "Zips descriptions."
    group = "descriptions"
    from(buildDescriptions)
    archiveFileName.set("descriptions.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
}

defaultTasks(packageDescriptions.name)
