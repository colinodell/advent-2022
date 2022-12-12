package com.colinodell.advent2022

import java.util.PriorityQueue

class Day12(input: List<String>) {
    private val grid = input.toGrid()

    private val start = grid.entries.find { it.value == 'S' }!!.key
    private val goal = grid.entries.find { it.value == 'E' }!!.key

    private enum class Direction {
        CLIMBING, DESCENDING;

        fun canGo(currentElevation: Int, nextElevation: Int): Boolean {
            return when (this) {
                CLIMBING -> (nextElevation - currentElevation) <= 1
                DESCENDING -> (currentElevation - nextElevation) <= 1
            }
        }
    }

    fun solvePart1() = findShortestPathLength(start, Direction.CLIMBING) { it == goal }
    fun solvePart2() = findShortestPathLength(goal, Direction.DESCENDING) { grid[it]!! == 'a' }

    private fun getElevation(c: Char) = when (c) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> c.code
    }

    private fun nextMoves(current: Vector2, direction: Direction) = sequence {
        for (neighbor in grid.neighborsOf(current)) {
            if (direction.canGo(getElevation(grid[current]!!), getElevation(neighbor.value))) {
                yield(neighbor)
            }
        }
    }

    /**
     * Find the shortest path using the Dijkstra algorithm
     */
    private fun findShortestPathLength(start: Vector2, direction: Direction, reachedEnd: (Vector2) -> Boolean): Int {
        var end: Vector2? = null

        val visited = mutableSetOf<Vector2>()
        val distances = mutableMapOf<Vector2, Int>()
        val previous = mutableMapOf<Vector2, Vector2>()
        val queue = PriorityQueue<Vector2> { a, b -> distances[a]!! - distances[b]!! }

        for (node in grid.keys) {
            distances[node] = Int.MAX_VALUE
            previous[node] = start
        }
        distances[start] = 0
        queue.add(start)

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            visited.add(current)

            if (reachedEnd(current)) {
                end = current
                break
            }

            for (neighbor in nextMoves(current, direction).map { it.key }) {
                val distance = distances[current]!! + 1
                if (distance < distances[neighbor]!!) {
                    distances[neighbor] = distance
                    previous[neighbor] = current
                    queue.add(neighbor)
                }
            }
        }

        // Determine the length of the path
        var pathLength = 0
        var current = end!!
        while (current != start) {
            pathLength++
            current = previous[current]!!
        }

        return pathLength
    }
}
