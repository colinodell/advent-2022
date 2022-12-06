package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsText
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 6: Tuning Trouble")
class Day06Test {
    private val exampleInput = inputAsText("day06_example.txt")
    private val puzzleInput = inputAsText("day06_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day06(exampleInput).solvePart1()).isEqualTo(11)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day06(puzzleInput).solvePart1()).isEqualTo(1210)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day06(exampleInput).solvePart2()).isEqualTo(26)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day06(puzzleInput).solvePart2()).isEqualTo(3476)
    }
}
