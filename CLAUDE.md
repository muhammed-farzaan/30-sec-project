# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Common Commands

```bash
# Build fat JAR (primary artifact)
./gradlew shadowJar
# Output: build/libs/thirty-sec-project-all.jar

# Run the application (requires a ride name argument)
./gradlew run --args="rollercoaster"

# Run the fat JAR directly
java -jar build/libs/thirty-sec-project-all.jar rollercoaster

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.anazzubair.gradle.MainTest"

# Build description files (copies + token-replaces descriptions/ → build/descriptions/)
./gradlew buildDescriptions

# Package descriptions into a zip
./gradlew packageDescriptions
```

Valid ride names (must match a resource file in `src/main/resources/`): `rollercoaster`, `logflume`, `teacups`.

## Architecture

**Single-module Kotlin app** (`com.anazzubair.gradle`) with three source files of note:

- [`src/main/kotlin/com/anazzubair/gradle/Main.kt`](src/main/kotlin/com/anazzubair/gradle/Main.kt) — entry point plus two pure functions: `getRideStatus(ride)` reads a classpath resource file whose name matches the ride and returns a random line; `readFile(filename)` loads that resource.
- [`src/main/resources/`](src/main/resources/) — one `.txt` file per ride, each line is a possible status string.
- [`src/test/kotlin/com/anazzubair/gradle/MainTest.kt`](src/test/kotlin/com/anazzubair/gradle/MainTest.kt) — Kotest `DescribeSpec` tests covering `readFile` and `getRideStatus` for all three rides.

**Build highlights:**

- Thin JAR tasks (`jar`, `distZip`, `distTar`, `installDist`) are disabled; the only distribution artifact is the Shadow fat JAR.
- Configuration cache is enabled (`gradle.properties`), so new tasks must be cache-compatible.
- Dependency versions are managed centrally in [`gradle/libs.versions.toml`](gradle/libs.versions.toml) (Kotlin 2.3.0, Shadow 9.4.1, Kotest 5.9.1).
- A custom `buildDescriptions` Copy task replaces the `@description@` token in `descriptions/*.txt` with `"I am learning Gradle"` and outputs to `build/descriptions/`.

**Testing:** Kotest with JUnit 5 platform. Tests live under the same package as production code and reference resource files by name (without extension) since `getRideStatus` appends `.txt` internally.
