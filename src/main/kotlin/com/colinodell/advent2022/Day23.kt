package com.colinodell.advent2022

class Day23(input: List<String>) {
    private val elves = input.toGrid().filter { it.value == '#' }.keys

    fun solvePart1() = simulate()
        .take(10)
        .last()
        .let { elves ->
            (elves.width() * elves.height()) - elves.count()
        }

    fun solvePart2() = simulate().indexOfLast { true } + 2 // +1 because we're 0-indexed, and another +1 because our sequence excludes the next state where everything stays the same

    private fun simulate() = sequence {
        var elves = elves
        var round = 0

        while (true) {
            // First half
            val proposedPositions = elves
                .associateWith { elf -> proposeNextMove(elf, elves, round) }
                .also { p ->
                    if (p.all { it.value == null }) {
                        // No moves to make, we're done!
                        return@sequence
                    }
                }
                .mapValues { (current, move) ->
                    move?.let { current + move } ?: current
                }

            // Second half
            val countAtPosition = proposedPositions.values.groupingBy { it }.eachCount()
            elves = proposedPositions.map { (originalPos, proposedPos) ->
                if (countAtPosition[proposedPos]!! > 1) {
                    originalPos
                } else {
                    proposedPos
                }
            }.toSet()

            yield(elves)
            round++
        }
    }

    private val proposalOffsets = listOf(
        listOf(Vector2(0, -1), Vector2(-1, -1), Vector2(1, -1)), // North
        listOf(Vector2(0, 1), Vector2(-1, 1), Vector2(1, 1)), // South
        listOf(Vector2(-1, 0), Vector2(-1, -1), Vector2(-1, 1)), // West
        listOf(Vector2(1, 0), Vector2(1, -1), Vector2(1, 1)) // East
    )

    /**
     * Returns the direction the elf should move, or null if it should stay put
     */
    private fun proposeNextMove(currentPos: Vector2, elves: Set<Vector2>, round: Int): Vector2? {
        // Are all surrounding spaces empty?
        if (currentPos.neighborsIncludingDiagonals().none { it in elves }) {
            return null
        }

        // Consider each of the four directions (in the order we prefer this round)
        for (i in 0..3) {
            val movesToConsider = proposalOffsets[(i + round) % 4]

            // Are any of these three spaces occupied?
            if (movesToConsider.none { elves.contains(currentPos + it) }) {
                return movesToConsider[0]
            }
        }

        return null
    }
}
