package com.colinodell.advent2022

import kotlin.math.min

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

    fun solvePart2() = simulate(1000000000000)

    private fun simulate(rocksToDrop: Long): Long {
        val fallenRocks = mutableSetOf<Vector2>()
        var rocksDropped = 0
        var jetIndex = -1
        var highestPoint = 0

        val seenCycles = mutableSetOf<Int>()
        var cycle: Cycle? = null
        val heightAfterNDroppedRocks = mutableListOf<Int>()

        // A cycle should be detected after (shape size * jet pattern size) iterations
        val repetitions = min(
            rocksToDrop,
            (shapes.size * jetPattern.size).toLong()
        )

        repeat(repetitions.toInt()) {
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

            // Try to detect a cycle
            // A cycle occurs when we continue seeing the same jet pattern index after dropping all five shapes
            if (rocksDropped % shapes.size == 0) {
                if (cycle == null) {
                    // We have not yet seen a cycle, so record the current jet pattern index for next time
                    if (!seenCycles.add(jetIndex)) {
                        cycle = Cycle(jetIndex, shape[0].y, rocksDropped)
                    }
                } else if (cycle!!.index == jetIndex) {
                    // The cycle is repeating!
                    val cycleHeight = shape[0].y - cycle!!.yPosition
                    val cycleSize = rocksDropped - cycle!!.rocksDropped

                    val rocksToJumpOver = rocksToDrop - cycle!!.rocksDropped
                    val cycleCount = rocksToJumpOver / cycleSize
                    val extraRocks = (rocksToJumpOver % cycleSize).toInt()

                    return (cycleCount * cycleHeight) + heightAfterNDroppedRocks[extraRocks]
                }
            }

            // Record the height of the rocks after this rock was dropped
            // We'll use this later when we detect a cycle
            if (cycle != null) {
                heightAfterNDroppedRocks.add(highestPoint)
            }
        }

        return highestPoint.toLong()
    }

    private fun hasCollision(shape: List<Vector2>, grid: Set<Vector2>): Boolean {
        return shape.any { it.x !in 0..6 || it.y <= 0 || it in grid }
    }

    private data class Cycle(val index: Int, val yPosition: Int, val rocksDropped: Int)
}
