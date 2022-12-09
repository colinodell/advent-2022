package com.colinodell.advent2022

class Day08(input: List<String>) {
    private val grid = input.toGrid { it.digitToInt() }

    private val directions = listOf(
        Vector2(1, 0), // right
        Vector2(0, 1), // down
        Vector2(-1, 0), // left
        Vector2(0, -1), // up
    )

    /**
     * Count the number of tress where, in at least one direction, there's nothing taller than the current tree
     */
    fun solvePart1() = grid.count { (pos, height) ->
        directions.any { dir ->
            grid.pointsToThe(dir, pos).none { it.value!! >= height }
        }
    }

    /**
     * Find the maximum value of the scenic score for each tree
     *
     * The scenic score is the product of trees visible in each cardinal direction
     */
    fun solvePart2() = grid.maxOf { (pos, height) ->
        directions.productOf { dir ->
            grid.pointsToThe(dir, pos).countUntil { it.value!! >= height }
        }
    }
}
