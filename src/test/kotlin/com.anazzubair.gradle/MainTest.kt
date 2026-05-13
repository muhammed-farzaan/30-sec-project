package com.anazzubair.gradle

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe

class MainTest : DescribeSpec({

    describe("readFile") {
        it("returns all lines from a valid resource file") {
            val lines = readFile("rollercoaster.txt")
            lines shouldBe listOf(
                "at the station",
                "climbing to the top",
                "on the precipice",
                "generating screams"
            )
        }

        it("reads lines from logflume.txt") {
            val lines = readFile("logflume.txt")
            lines.shouldNotBeEmpty()
            lines shouldContain "soaking passengers"
        }

        it("reads lines from teacups.txt") {
            val lines = readFile("teacups.txt")
            lines shouldBe listOf(
                "not spinning",
                "spinning",
                "super-spin vomit mode"
            )
        }

        it("throws IllegalArgumentException for a missing resource") {
            val ex = shouldThrow<IllegalArgumentException> {
                readFile("unknown-ride.txt")
            }
            ex.message shouldBe "Ride not found: unknown-ride.txt"
        }
    }

    describe("getRideStatus") {
        it("returns a known status for rollercoaster") {
            val knownStatuses = listOf(
                "at the station",
                "climbing to the top",
                "on the precipice",
                "generating screams"
            )
            val status = getRideStatus("rollercoaster")
            knownStatuses shouldContain status
        }

        it("returns a known status for logflume") {
            val knownStatuses = listOf(
                "at the station",
                "climbing to the top",
                "on the precipice",
                "soaking passengers"
            )
            val status = getRideStatus("logflume")
            knownStatuses shouldContain status
        }

        it("returns a known status for teacups") {
            val knownStatuses = listOf(
                "not spinning",
                "spinning",
                "super-spin vomit mode"
            )
            val status = getRideStatus("teacups")
            knownStatuses shouldContain status
        }

        it("throws IllegalArgumentException for an unknown ride") {
            shouldThrow<IllegalArgumentException> {
                getRideStatus("ghost-train")
            }
        }
    }
})
