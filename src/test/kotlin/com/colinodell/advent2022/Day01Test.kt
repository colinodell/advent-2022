package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsText
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 1: Calorie Counting")
class Day01Test {
    private val exampleInput = inputAsText("day01_example.txt")
    private val puzzleInput = inputAsText("day01_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day01(exampleInput).solvePart1()).isEqualTo(24000)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day01(puzzleInput).solvePart1()).isEqualTo(68442)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day01(exampleInput).solvePart2()).isEqualTo(45000)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day01(puzzleInput).solvePart2()).isEqualTo(204837)
    }
}
