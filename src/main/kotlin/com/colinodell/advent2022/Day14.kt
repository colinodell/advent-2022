package com.colinodell.advent2022

class Day14(input: List<String>) {
    private val grid = input.flatMap { line ->
        line.split(" -> ")
            // Things in between -> arrows are points
            .map { point -> point.split(",").let { (x, y) -> Vector2(x.toInt(), y.toInt()) } }
            // Group each point with the following one
            .zipWithNext()
            // Create a line from them
            .map { (a, b) -> Line(a, b) }
            // And then gather all the points that are part of those lines
            .flatMap { l -> l.points }
    }.toGrid { '#' }

    private val sandStart = Vector2(500, 0)
    private val maxY = grid.keys.maxOf { it.y }

    fun solvePart1() = dropAllSand(null, maxY)

    fun solvePart2() = dropAllSand(maxY + 2, null) + 1

    private fun nextPositions(current: Vector2) = sequence {
        yield(current + Vector2(0, 1))
        yield(current + Vector2(-1, 1))
        yield(current + Vector2(1, 1))
    }

    /**
     * Continue dropping sand until we fall into the void or reach the source point
     */
    private fun dropAllSand(floorY: Int?, voidY: Int?): Int {
        var pos = sandStart
        var count = 0
        while (true) {
            val next = nextPositions(pos).firstOrNull { grid[it] == null }
            pos = when {
                // End condition 1: We fell into the void
                next != null && next.y == voidY -> return count
                // End condition 2: We reached the source point
                next == null && pos == sandStart -> return count
                // The sand unit is now at rest
                next == null || next.y == floorY -> {
                    // Record it
                    grid[pos] = 'o'
                    count++
                    // Drop the next sand unit
                    sandStart
                }
                // The sand unit is still falling
                else -> next
            }
        }
    }
}
