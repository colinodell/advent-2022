package com.colinodell.advent2022

class Day17(input: String) {
    private val jetPattern = input.asSequence().repeatForever().iterator()

    private val shapes = sequence {
        yield(Shape("####"))
        yield(Shape(".#.\n###\n.#."))
        yield(Shape("..#\n..#\n###"))
        yield(Shape("#\n#\n#\n#"))
        yield(Shape("##\n##"))
    }.repeatForever().iterator()

    fun solvePart1() = simulate(2022)

    private fun simulate(rocks: Int): Int {
        val grid = mutableSetOf<Vector2>()
        repeat(rocks) {
            shapes.next().dropInto(grid, jetPattern)
        }

        return grid.maxOf { it.y }
    }

    private class Shape(ascii: String) {
        private val height = ascii.lines().size
        private var points =
            ascii.lines().flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, c ->
                    if (c == '#') Vector2(x, height - y) else null
                }
            }

        fun dropInto(grid: MutableSet<Vector2>, jetPattern: Iterator<Char>) {
            // The bottom edge should start three units above the highest rock in the room (or the floor, if there isn't one).
            val highestPointInGrid = grid.maxOfOrNull { it.y } ?: 0
            points = points.map { it + Vector2(2, highestPointInGrid + 3) }

            while (true) {
                // Move left/right with the jet pattern
                val nextJetPattern = when (jetPattern.next()) {
                    '<' -> Vector2(-1, 0)
                    '>' -> Vector2(1, 0)
                    else -> error("Invalid jet pattern")
                }
                var nextPoints = points.map { it + nextJetPattern }
                // Are we still in bounds and not overlapping anything?
                if (nextPoints.all { it.x in 0..6 && it !in grid }) {
                    points = nextPoints
                }

                // Move downwards
                nextPoints = points.map { it + Vector2(0, -1) }
                // Are we not overlapping anything?
                if (nextPoints.all { it.y > 0 && it !in grid }) {
                    points = nextPoints
                } else {
                    // We've hit something, so we're done
                    break
                }
            }

            // Add our points to the grid
            points.forEach { grid.add(it) }
        }
    }
}
