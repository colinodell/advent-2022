package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsText
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 17: Pyroclastic Flow")
class Day17Test {
    private val exampleInput = inputAsText("day17_example.txt")
    private val puzzleInput = inputAsText("day17_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day17(exampleInput).solvePart1()).isEqualTo(3068)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day17(puzzleInput).solvePart1()).isEqualTo(3179)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day17(exampleInput).solvePart2()).isEqualTo(1514285714288)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day17(puzzleInput).solvePart2()).isEqualTo(1567723342929)
    }
}
