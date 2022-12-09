package com.colinodell.advent2022

class Day09(input: List<String>) {
    private val directions = mapOf(
        "L" to Vector2(-1, 0),
        "R" to Vector2(1, 0),
        "U" to Vector2(0, -1),
        "D" to Vector2(0, 1),
    )

    /**
     * Generate a sequence of all points the head knot passes through
     */
    private val headKnotPositions = sequence {
        var pos = Vector2(0, 0)
        yield(pos)

        for (line in input) {
            val (dir, count) = line.split(" ")
            repeat(count.toInt()) {
                pos += directions[dir]!!
                yield(pos)
            }
        }
    }

    fun solvePart1() = solveFor(2)
    fun solvePart2() = solveFor(10)

    /**
     * Count the number of distinct points the tail knot passes through
     */
    private fun solveFor(knotCount: Int) = createRope(knotCount).last().distinct().count()

    /**
     * Create a sequence where each subsequent knot follows the one before it
     */
    private fun createRope(knotCount: Int) = generateSequence(headKnotPositions) { follow(it) }.take(knotCount)

    /**
     * Follow the given knot, yielding the points it passes through
     */
    private fun follow(leadingKnot: Sequence<Vector2>) = sequence {
        var followerPos = Vector2(0, 0)
        yield(followerPos)

        for (leadPos in leadingKnot) {
            while (!followerPos.isTouching(leadPos)) {
                // Move towards the head position one step at a time
                followerPos += (leadPos - followerPos).normalize()
                yield(followerPos)
            }
        }
    }
}
