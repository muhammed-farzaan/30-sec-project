
plugins {
    base
    kotlin("jvm") version "2.3.0"
    application
    id("com.gradleup.shadow") version "9.4.1"
}

application {
    mainClass = "com.anazzubair.gradle.MainKt"
}

tasks {
    //disalbe the thin jar and related tasks from application plugin
    jar { enabled = false }
    distZip { enabled = false }
    distTar { enabled = false }
    installDist { enabled = false }

    //depend on shadow plugin for distribution
    shadowJar {
        archiveClassifier = "all"
        mergeServiceFiles()
    }
}



val buildDescriptions by tasks.registering(Copy::class) {
    description = "Copies descriptions to build directory. Replaces tokens."
    group = "descriptions"
    dependsOn(tasks.clean)
    from("descriptions") {
        include("*.txt")
    }
    into(layout.buildDirectory.dir("descriptions"))
    filter<org.apache.tools.ant.filters.ReplaceTokens>(
        mapOf("tokens" to mapOf("description" to "I am learning Gradle"))
    )
}

val packageDescriptions by tasks.registering(Zip::class) {
    description = "Zips descriptions."
    group = "descriptions"
    from(buildDescriptions)
    archiveFileName.set("descriptions.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
}
