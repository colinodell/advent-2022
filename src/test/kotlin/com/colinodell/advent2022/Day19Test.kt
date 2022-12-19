package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 19: Not Enough Minerals")
class Day19Test {
    private val exampleInput = inputAsListOfString("day19_example.txt")
    private val puzzleInput = inputAsListOfString("day19_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day19(exampleInput).solvePart1()).isEqualTo(33)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day19(puzzleInput).solvePart1()).isEqualTo(1262)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day19(exampleInput).solvePart2()).isEqualTo(3472)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day19(puzzleInput).solvePart2()).isEqualTo(37191)
    }
}
