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

    fun solvePart1(): Int {
        var sandUnits = 0
        while (dropOneSandUnit(null, maxY)) {
            sandUnits++
        }
        return sandUnits
    }

    fun solvePart2(): Int {
        var sandUnits = 0
        while (!grid.keys.contains(sandStart)) {
            dropOneSandUnit(maxY + 2, null)
            sandUnits++
        }
        return sandUnits
    }

    private fun nextPositions(current: Vector2) = sequence {
        yield(current + Vector2(0, 1))
        yield(current + Vector2(-1, 1))
        yield(current + Vector2(1, 1))
    }

    /**
     * Drop a single sand unit until it comes to a rest
     *
     * @return true if the sand unit came to a rest, false if it fell into the void
     */
    private fun dropOneSandUnit(floorY: Int?, voidY: Int?): Boolean {
        var pos = sandStart
        while (true) {
            // Move the sand unit into the next position (down, down-left, or down-right), if able; break otherwise
            pos = nextPositions(pos).firstOrNull { grid[it] == null && (floorY == null || it.y < floorY) } ?: break
            // Have we fallen into the void?
            if (floorY == null && pos.y == voidY) {
                return false
            }
        }
        grid[pos] = 'o'
        return true
    }
}
