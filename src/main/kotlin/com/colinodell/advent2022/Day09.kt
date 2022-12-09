package com.colinodell.advent2022

class Day09(input: List<String>) {
    private val directions = mapOf(
        "L" to Vector2(-1, 0),
        "R" to Vector2(1, 0),
        "U" to Vector2(0, -1),
        "D" to Vector2(0, 1),
    )

    private val steps = input.map {
        val parts = it.split(" ")
        Pair(directions[parts[0]]!!, parts[1].toInt())
    }

    fun solvePart1() = solve(2)
    fun solvePart2() = solve(10)

    private fun solve(n: Int): Int {
        // Create a list with all knots
        val allKnots = List(n) { Vector2(0, 0) }.toMutableList()
        // And a set to track where the tail has been
        val tailLocations = mutableSetOf(allKnots.last())

        // Move the head knot according to our input
        for ((dir, count) in steps) {
            // Move in the given direction (`dir`) `count` times
            repeat(count) {
                // The first knot always moves in the direction given
                allKnots[0] += dir
                // All other knots only move towards the previous one if they aren't touching
                for (i in 1 until n) {
                    if (!allKnots[i].isTouching(allKnots[i - 1])) {
                        allKnots[i] += (allKnots[i - 1] - allKnots[i]).normalize()
                    }
                }
                // Store the position of the tail knot
                tailLocations.add(allKnots.last())
            }
        }

        return tailLocations.size
    }
}
