package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 20: Grove Positioning System")
class Day20Test {
    private val exampleInput = inputAsListOfString("day20_example.txt").map { it.toInt() }
    private val puzzleInput = inputAsListOfString("day20_input.txt").map { it.toInt() }

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day20(exampleInput).solvePart1()).isEqualTo(3)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day20(puzzleInput).solvePart1()).isEqualTo(10763)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day20(exampleInput).solvePart2()).isEqualTo(1623178306)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day20(puzzleInput).solvePart2()).isEqualTo(4979911042808)
    }
}
