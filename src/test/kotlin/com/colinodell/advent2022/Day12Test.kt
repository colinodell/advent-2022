package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 12: Hill Climbing Algorithm")
class Day12Test {
    private val exampleInput = inputAsListOfString("day12_example.txt")
    private val puzzleInput = inputAsListOfString("day12_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day12(exampleInput).solvePart1()).isEqualTo(31)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day12(puzzleInput).solvePart1()).isEqualTo(339)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day12(exampleInput).solvePart2()).isEqualTo(29)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day12(puzzleInput).solvePart2()).isEqualTo(332)
    }
}
