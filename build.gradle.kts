import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("base")
}

tasks.register<Copy>("buildDescriptions") {
    dependsOn("clean")
    from("descriptions") {
        include("*.txt")
    }
    into(layout.buildDirectory.dir("descriptions"))
    filter<ReplaceTokens>(mapOf("tokens" to mapOf("description" to "I am learning Gradle")))
}

tasks.register<Zip>("packageDescriptions") {
    from(tasks.named("buildDescriptions"))
    archiveFileName.set("descriptions.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
}