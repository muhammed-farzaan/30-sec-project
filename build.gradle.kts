import org.apache.tools.ant.filters.ReplaceTokens

tasks.register<Copy>("copySomething") {
    from("something.txt") {
        filter<ReplaceTokens>("tokens" to mapOf("something" to "this a"))
    }
    into(layout.buildDirectory)
    println("Copied something.txt to ${layout.buildDirectory})
    println("Content of something.txt: ${file("something.txt").readText()}")
}