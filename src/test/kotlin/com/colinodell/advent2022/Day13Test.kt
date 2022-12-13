package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 13: Distress Signal")
class Day13Test {
    private val exampleInput = inputAsListOfString("day13_example.txt")
    private val puzzleInput = inputAsListOfString("day13_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day13(exampleInput).solvePart1()).isEqualTo(13)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day13(puzzleInput).solvePart1()).isEqualTo(5292)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day13(exampleInput).solvePart2()).isEqualTo(140)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day13(puzzleInput).solvePart2()).isEqualTo(23868)
    }
}
