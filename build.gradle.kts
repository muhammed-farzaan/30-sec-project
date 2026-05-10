import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    base
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
