package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 8: Treetop Tree House")
class Day08Test {
    private val exampleInput = inputAsListOfString("day08_example.txt")
    private val puzzleInput = inputAsListOfString("day08_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day08(exampleInput).solvePart1()).isEqualTo(21)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day08(puzzleInput).solvePart1()).isEqualTo(1816)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day08(exampleInput).solvePart2()).isEqualTo(8)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day08(puzzleInput).solvePart2()).isEqualTo(383520)
    }
}
