package com.anazzubair.gradle

import java.io.IOException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("A single rider need to be passed.")
        exitProcess(1)
    }

    val rideName = args[0]
    val rideStatus = getRideStatus(rideName)
    println("Current status of $rideName is '$rideStatus'")
}

fun getRideStatus(ride: String): String {
    val rideStatuses = readFile("$ride.txt")
    return rideStatuses.randomOrNull() ?: "No status available"
}

fun readFile(filename: String): List<String> {
    val resource = Thread.currentThread().contextClassLoader.getResource(filename)
    requireNotNull(resource) { "Ride not found: $filename" }

    return try {
        resource.openStream().bufferedReader().use { it.readLines() }
    } catch (e: IOException) {
        throw RuntimeException("Error reading $filename", e)
    }
}
