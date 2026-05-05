import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("base")
}

tasks.register<Copy>("buildDescriptions") {
    description = "Copies descriptions to build directory. Replaces tokens."
    group = "descriptions"
    dependsOn("clean")
    from("descriptions") {
        include("*.txt")
    }
    into(layout.buildDirectory.dir("descriptions"))
    filter<ReplaceTokens>(mapOf("tokens" to mapOf("description" to "I am learning Gradle")))
}

tasks.register<Zip>("packageDescriptions") {
    description = "Zips descriptions."
    group = "descriptions"
    from(tasks.named("buildDescriptions"))
    archiveFileName.set("descriptions.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
}