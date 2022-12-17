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
        listOf(Vector2(0, 0), Vector2(1, 0), Vector2(2, 0), Vector2(3, 0)),
        listOf(Vector2(0, 1), Vector2(1, 0), Vector2(1, 1), Vector2(2, 1), Vector2(1, 2)),
        listOf(Vector2(0, 0), Vector2(1, 0), Vector2(2, 0), Vector2(2, 1), Vector2(2, 2)),
        listOf(Vector2(0, 0), Vector2(0, 1), Vector2(0, 2), Vector2(0, 3)),
        listOf(Vector2(0, 0), Vector2(0, 1), Vector2(1, 0), Vector2(1, 1)),
    )

    fun solvePart1() = simulate(2022)

    private fun simulate(rocksToDrop: Int): Int {
        val fallenRocks = mutableSetOf<Vector2>()
        var rocksDropped = 0
        var jetIndex = -1
        var highestPoint = 0

        repeat(rocks) {
            // The bottom edge should start three units above the highest rock in the room (or the floor, if there isn't one).
            var shape = shapes[rocksDropped % shapes.size].map { it + Vector2(2, highestPoint + 4) }

            while (true) {
                // Move left/right with the jet pattern
                jetIndex = (jetIndex + 1) % jetPattern.size
                var candidatePosition = shape.map { it + jetPattern[jetIndex] }
                // Only move if there's no collision
                if (!hasCollision(candidatePosition, fallenRocks)) {
                    shape = candidatePosition
                }

                // Move downwards
                candidatePosition = shape.map { it + Vector2(0, -1) }
                // Only move if there's no collision
                if (!hasCollision(candidatePosition, fallenRocks)) {
                    shape = candidatePosition
                } else {
                    break // We've hit something, so we're done with this rock
                }
            }

            // Add this rock to our list of fallen rocks
            rocksDropped++
            fallenRocks.addAll(shape)
            highestPoint = fallenRocks.maxOf { it.y }
        }

        return highestPoint
    }

    private fun hasCollision(shape: List<Vector2>, grid: Set<Vector2>): Boolean {
        return shape.any { it.x !in 0..6 || it.y <= 0 || it in grid }
    }
}
