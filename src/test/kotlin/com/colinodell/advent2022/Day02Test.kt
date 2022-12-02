package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 2: Rock Paper Scissors")
class Day02Test {
    private val exampleInput = inputAsListOfString("day02_example.txt")
    private val puzzleInput = inputAsListOfString("day02_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day02(exampleInput).solvePart1()).isEqualTo(15)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day02(puzzleInput).solvePart1()).isEqualTo(15632)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day02(exampleInput).solvePart2()).isEqualTo(12)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day02(puzzleInput).solvePart2()).isEqualTo(14416)
    }
}
