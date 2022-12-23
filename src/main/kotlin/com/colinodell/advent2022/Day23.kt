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
            val proposedMoves = elves.associateWith { elf -> proposeNextMove(elf, elves, round) }
            if (proposedMoves.all { it.value == null }) {
                break
            }
            val proposedPositions = proposedMoves.mapValues { (current, move) -> move?.let { current + move } ?: current }

            // Second half
            elves = mutableSetOf()
            val countProposedPositions = proposedPositions.values.groupingBy { it }.eachCount()
            proposedPositions.forEach { (originalPos, proposedPos) ->
                if (countProposedPositions[proposedPos]!! > 1) {
                    elves.add(originalPos)
                } else {
                    elves.add(proposedPos)
                }
            }

            yield(elves)
            round++
        }
    }

    private val dirsToConsider = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

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
            val dirToConsider = dirsToConsider[(i + round) % 4].vector()
            // Generate the diagonal moves too
            val dirsToInspect = when {
                dirToConsider.x == 0 -> listOf(dirToConsider, dirToConsider + Vector2(1, 0), dirToConsider + Vector2(-1, 0))
                dirToConsider.y == 0 -> listOf(dirToConsider, dirToConsider + Vector2(0, 1), dirToConsider + Vector2(0, -1))
                else -> throw IllegalStateException("Unexpected direction: $dirToConsider")
            }

            // Are any of these three spaces occupied?
            if (dirsToInspect.any { elves.contains(currentPos + it) }) {
                continue
            }

            return dirToConsider
        }

        return null
    }
}
