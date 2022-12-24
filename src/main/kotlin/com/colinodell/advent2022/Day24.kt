package com.colinodell.advent2022

import java.util.PriorityQueue

class Day24(input: List<String>) {
    private val grid = input.toGrid()
    private val innerRegion = grid.region().contract(1)
    private val start = grid.toList().first { it.first.y == 0 && it.second == '.' }.first
    private val goal = grid.toList().first { it.first.y > innerRegion.bottomRight.y && it.second == '.' }.first
    private val roundTripEstimate = start.manhattanDistanceTo(goal)

    fun solvePart1() = solve(listOf(goal))
    fun solvePart2() = solve(listOf(goal, start, goal))

    private fun solve(goals: List<Vector2>): Int {
        val initialState = State(start, 0, 0)

        val queue = PriorityQueue<State>(
            compareBy { s ->
                // Our heuristic is basically the sum of:
                //   1. The time spent so far
                //   2. The distance to the current goal
                //   3. The distance between any remaining goals
                // Parts 2 and 3 of this are multiplied by 2 since there's a higher likelihood of needing to wait or backtrack
                s.time + 2 * (s.pos.manhattanDistanceTo(goals[s.goalIndex]) + roundTripEstimate * (goals.size - s.goalIndex - 1))
            }
        )
        queue.add(State(start, 0, 0))

        val visited = mutableSetOf(initialState)
        while (queue.isNotEmpty()) {
            val current = queue.poll()

            // Have we reached one of our goals?
            if (current.pos == goals[current.goalIndex]) {
                if (current.goalIndex == goals.size - 1) {
                    // We've reached the end!
                    return current.time
                }
                // We've reached an intermediate goal
                current.goalIndex++
            }

            // Try moving (or staying put) unless we've previously attempted this state
            for (next in current.next().filter { visited.add(it) }) {
                // Make sure our move is in bounds
                if (!(next.pos in innerRegion || next.pos == start || next.pos == goal)) {
                    continue
                }

                // Make sure there's no blizzards in the way
                if (next.pos in blizzardsAt(next.time)) {
                    continue
                }

                queue.add(next)
            }
        }

        error("No solution found")
    }

    private data class State(val pos: Vector2, val time: Int, var goalIndex: Int) {
        fun next() = setOf(
            State(pos + Vector2(1, 0), time + 1, goalIndex), // Right
            State(pos + Vector2(-1, 0), time + 1, goalIndex), // Left
            State(pos + Vector2(0, 1), time + 1, goalIndex), // Up
            State(pos + Vector2(0, -1), time + 1, goalIndex), // Down
            State(pos, time + 1, goalIndex) // Wait
        )
    }

    private data class Blizzard(val pos: Vector2, val dir: Direction)

    private val blizzardPositionCache = mutableListOf<Set<Vector2>>()
    private fun blizzardsAt(time: Int): Set<Vector2> {
        while (blizzardPositionCache.size <= time) {
            blizzardPositionCache.add(blizzardGenerator.next())
        }
        return blizzardPositionCache[time]
    }

    private val blizzardGenerator = sequence {
        var blizzards = grid
            .filter { it.value in setOf('<', '>', 'v', '^') }
            .map { (p, c) -> Blizzard(p, Direction.from(c)) }

        while (true) {
            yield(blizzards.map { it.pos }.toSet())

            blizzards = blizzards.map { b ->
                val nextPos = b.pos + b.dir.vector()
                when {
                    nextPos in innerRegion -> Blizzard(nextPos, b.dir)
                    b.dir == Direction.UP -> Blizzard(nextPos.withY(innerRegion.bottomRight.y), b.dir)
                    b.dir == Direction.DOWN -> Blizzard(nextPos.withY(innerRegion.topLeft.y), b.dir)
                    b.dir == Direction.LEFT -> Blizzard(nextPos.withX(innerRegion.bottomRight.x), b.dir)
                    b.dir == Direction.RIGHT -> Blizzard(nextPos.withX(innerRegion.topLeft.x), b.dir)
                    else -> error("Invalid direction: ${b.dir}")
                }
            }
        }
    }.iterator()
}
