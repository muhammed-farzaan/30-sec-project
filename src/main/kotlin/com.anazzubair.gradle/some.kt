package com.anazzubair.gradle

fun main() {
    val some = List(20) { it }
    println(some.windowed(2))
}