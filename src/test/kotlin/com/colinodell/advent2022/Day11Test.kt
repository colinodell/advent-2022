package com.colinodell.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 11: Monkey in the Middle")
class Day11Test {
    private val exampleInput = listOf(
        Day11.Monkey(mutableListOf(79, 98), { it * 19 }, 23, 2, 3),
        Day11.Monkey(mutableListOf(54, 65, 75, 74), { it + 6 }, 19, 2, 0),
        Day11.Monkey(mutableListOf(79, 60, 97), { it * it }, 13, 1, 3),
        Day11.Monkey(mutableListOf(74), { it + 3 }, 17, 0, 1),
    )

    private val puzzleInput = listOf(
        Day11.Monkey(mutableListOf(71, 86), { it * 13 }, 19, 6, 7),
        Day11.Monkey(mutableListOf(66, 50, 90, 53, 88, 85), { it + 3 }, 2, 5, 4),
        Day11.Monkey(mutableListOf(97, 54, 89, 62, 84, 80, 63), { it + 6 }, 13, 4, 1),
        Day11.Monkey(mutableListOf(82, 97, 56, 92), { it + 2 }, 5, 6, 0),
        Day11.Monkey(mutableListOf(50, 99, 67, 61, 86), { it * it }, 7, 5, 3),
        Day11.Monkey(mutableListOf(61, 66, 72, 55, 64, 53, 72, 63), { it + 4 }, 11, 3, 0),
        Day11.Monkey(mutableListOf(59, 79, 63), { it * 7 }, 17, 2, 7),
        Day11.Monkey(mutableListOf(55), { it + 7 }, 3, 2, 1),
    )

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day11(exampleInput).solvePart1()).isEqualTo(10605)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day11(puzzleInput).solvePart1()).isEqualTo(88208)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day11(exampleInput).solvePart2()).isEqualTo(2713310158)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day11(puzzleInput).solvePart2()).isEqualTo(21115867968)
    }
}
