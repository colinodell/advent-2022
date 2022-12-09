package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 9: Rope Bridge")
class Day09Test {
    private val exampleInput = inputAsListOfString("day09_example.txt")
    private val exampleInput2 = inputAsListOfString("day09_example2.txt")
    private val puzzleInput = inputAsListOfString("day09_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day09(exampleInput).solvePart1()).isEqualTo(13)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day09(puzzleInput).solvePart1()).isEqualTo(6311)
    }

    @Test
    fun `Part 2 - Example 1`() {
        assertThat(Day09(exampleInput).solvePart2()).isEqualTo(1)
    }

    @Test
    fun `Part 2 - Example 2`() {
        assertThat(Day09(exampleInput2).solvePart2()).isEqualTo(36)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day09(puzzleInput).solvePart2()).isEqualTo(2482)
    }
}
