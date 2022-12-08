package com.colinodell.advent2022

import com.colinodell.advent2021.Vector2
import com.colinodell.advent2021.pointsToThe
import com.colinodell.advent2021.toGrid

class Day08(input: List<String>) {
    private val grid = input.toGrid { it.digitToInt() }

    companion object {
        private val NORTH = Vector2(0, -1)
        private val SOUTH = Vector2(0, 1)
        private val EAST = Vector2(1, 0)
        private val WEST = Vector2(-1, 0)
    }

    fun solvePart1() = grid.count { (pos, height) ->
        grid.pointsToThe(NORTH, pos).none { it.value!! >= height } ||
            grid.pointsToThe(SOUTH, pos).none { it.value!! >= height } ||
            grid.pointsToThe(EAST, pos).none { it.value!! >= height } ||
            grid.pointsToThe(WEST, pos).none { it.value!! >= height }
    }

    fun solvePart2() = grid.maxOf { (pos, height) ->
        listOf(
            grid.pointsToThe(NORTH, pos).stopOnce { it.value!! >= height }.count(),
            grid.pointsToThe(SOUTH, pos).stopOnce { it.value!! >= height }.count(),
            grid.pointsToThe(EAST, pos).stopOnce { it.value!! >= height }.count(),
            grid.pointsToThe(WEST, pos).stopOnce { it.value!! >= height }.count()
        ).reduce(Int::times)
    }
}
