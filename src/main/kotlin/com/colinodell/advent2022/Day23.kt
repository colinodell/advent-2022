package com.colinodell.advent2022

class Day23(input: List<String>) {
    private val elves = input.toGrid().filter { it.value == '#' }.keys

    fun solvePart1() = simulate()
        .take(10)
        .last()
        .let { elves ->
            (elves.width() * elves.height()) - elves.count()
        }

    fun solvePart2() = simulate()
        .indexOfLast { true } + 2 // +1 because we're 0-indexed, and another +1 because our sequence excludes the next state where everything stays the same

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

    // Generate a list of the cardinal directions along with their diagonals
    private val dirsToConsider =
        listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
            .map { it.vector() }
            .map {
                when {
                    it.x == 0 -> listOf(it, it + Vector2(1, 0), it + Vector2(-1, 0))
                    it.y == 0 -> listOf(it, it + Vector2(0, 1), it + Vector2(0, -1))
                    else -> error("Unexpected direction: $it")
                }
            }

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
            val movesToConsider = dirsToConsider[(i + round) % 4]

            // Are any of these three spaces occupied?
            if (movesToConsider.none { elves.contains(currentPos + it) }) {
                return movesToConsider[0]
            }
        }

        return null
    }
}
