package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 22: Monkey Map")
class Day22Test {
    private val exampleInput = inputAsListOfString("day22_example.txt")
    private val puzzleInput = inputAsListOfString("day22_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day22(exampleInput).solvePart1()).isEqualTo(6032)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day22(puzzleInput).solvePart1()).isEqualTo(136054)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day22(exampleInput).solvePart2()).isEqualTo(5031)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day22(puzzleInput).solvePart2()).isEqualTo(122153)
    }
}
