package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 24: Blizzard Basin")
class Day24Test {
    private val exampleInput = inputAsListOfString("day24_example.txt")
    private val puzzleInput = inputAsListOfString("day24_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day24(exampleInput).solvePart1()).isEqualTo(18)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day24(puzzleInput).solvePart1()).isEqualTo(295)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day24(exampleInput).solvePart2()).isEqualTo(54)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day24(puzzleInput).solvePart2()).isEqualTo(851)
    }
}
