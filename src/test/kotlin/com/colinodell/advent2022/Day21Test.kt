package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 21: Monkey Math")
class Day21Test {
    private val exampleInput = inputAsListOfString("day21_example.txt")
    private val puzzleInput = inputAsListOfString("day21_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day21(exampleInput).solvePart1()).isEqualTo(152)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day21(puzzleInput).solvePart1()).isEqualTo(31017034894002)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day21(exampleInput).solvePart2()).isEqualTo(301)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day21(puzzleInput).solvePart2()).isEqualTo(3555057453229)
    }
}
