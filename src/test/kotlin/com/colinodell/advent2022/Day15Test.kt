package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 15: Beacon Exclusion Zone")
class Day15Test {
    private val exampleInput = inputAsListOfString("day15_example.txt")
    private val puzzleInput = inputAsListOfString("day15_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day15(exampleInput).solvePart1(10)).isEqualTo(26)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day15(puzzleInput).solvePart1(2000000)).isEqualTo(5299855)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day15(exampleInput).solvePart2(20)).isEqualTo(56000011)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day15(puzzleInput).solvePart2(4000000)).isEqualTo(13615843289729L)
    }
}
