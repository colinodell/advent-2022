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

    fun solvePart1() = follow(headKnot, 1).distinct().count()
    fun solvePart2() = follow(headKnot, 9).distinct().count()

    private val headKnot = sequence {
        var pos = Vector2(0, 0)
        yield(pos)

        for (step in steps) {
            repeat(step.second) {
                pos += step.first
                yield(pos)
            }
        }
    }

    /**
     * Generate a rope with (followerCount+1) knots and follow the tail knot
     */
    private fun follow(headKnotPositions: Sequence<Vector2>, followerCount: Int) = sequence {
        var seq = headKnotPositions
        repeat(followerCount) {
            seq = follow(seq)
        }
        yieldAll(seq)
    }

    /**
     * Generate a sequence of positions to follow the given knot
     */
    private fun follow(positions: Sequence<Vector2>) = sequence {
        var followerPos = Vector2(0, 0)
        yield(followerPos)

        for (leadPos in positions) {
            while (!followerPos.isTouching(leadPos)) {
                // Move towards the head position one step at a time
                followerPos += (leadPos - followerPos).normalize()
                yield(followerPos)
            }
        }
    }
}
