package com.colinodell.advent2022

class Day17(input: String) {
    private val jetPattern = input.map {
        when (it) {
            '<' -> Vector2(-1, 0)
            '>' -> Vector2(1, 0)
            else -> error("Invalid jet pattern")
        }
    }

    private val shapes = listOf(
        listOf(Vector2(0, 1), Vector2(1, 1), Vector2(2, 1), Vector2(3, 1)),
        listOf(Vector2(1, 3), Vector2(0, 2), Vector2(1, 2), Vector2(2, 2), Vector2(1, 1)),
        listOf(Vector2(2, 3), Vector2(2, 2), Vector2(0, 1), Vector2(1, 1), Vector2(2, 1)),
        listOf(Vector2(0, 4), Vector2(0, 3), Vector2(0, 2), Vector2(0, 1)),
        listOf(Vector2(0, 2), Vector2(1, 2), Vector2(0, 1), Vector2(1, 1)),
    )

    fun solvePart1() = simulate(2022)

    private fun simulate(rocks: Int): Int {
        val grid = mutableSetOf<Vector2>()
        var shapeIndex = 0
        var jetIndex = 0
        repeat(rocks) {
            var shape = shapes[shapeIndex++ % shapes.size]
            // The bottom edge should start three units above the highest rock in the room (or the floor, if there isn't one).
            val highestPointInGrid = grid.maxOfOrNull { it.y } ?: 0
            shape = shape.map { it + Vector2(2, highestPointInGrid + 3) }

            while (true) {
                // Move left/right with the jet pattern
                val nextJetPattern = jetPattern[jetIndex++ % jetPattern.size]
                var nextPoints = shape.map { it + nextJetPattern }
                // Are we still in bounds and not overlapping anything?
                if (nextPoints.all { it.x in 0..6 && it !in grid }) {
                    shape = nextPoints
                }

                // Move downwards
                nextPoints = shape.map { it + Vector2(0, -1) }
                // Are we not overlapping anything?
                if (nextPoints.all { it.y > 0 && it !in grid }) {
                    shape = nextPoints
                } else {
                    // We've hit something, so we're done
                    break
                }
            }

            // Add our points to the grid
            grid.addAll(shape)
        }

        return grid.maxOf { it.y }
    }
}
