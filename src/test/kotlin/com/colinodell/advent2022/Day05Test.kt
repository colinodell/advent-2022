package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsText
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 5: Supply Stacks")
class Day05Test {
    private val exampleInput = inputAsText("day05_example.txt")
    private val puzzleInput = inputAsText("day05_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day05(exampleInput).solvePart1()).isEqualTo("CMZ")
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day05(puzzleInput).solvePart1()).isEqualTo("GRTSWNJHH")
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day05(exampleInput).solvePart2()).isEqualTo("MCD")
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day05(puzzleInput).solvePart2()).isEqualTo("QLFQDBBHM")
    }
}
