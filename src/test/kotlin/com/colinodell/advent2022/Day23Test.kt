package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 23: Unstable Diffusion")
class Day23Test {
    private val exampleMinimalInput = inputAsListOfString("day23_example_minimal.txt")
    private val exampleInput = inputAsListOfString("day23_example.txt")
    private val puzzleInput = inputAsListOfString("day23_input.txt")

    @Test
    fun `Part 1 - Example (Minimal)`() {
        assertThat(Day23(exampleMinimalInput).solvePart1()).isEqualTo(25)
    }

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day23(exampleInput).solvePart1()).isEqualTo(110)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day23(puzzleInput).solvePart1()).isEqualTo(4109)
    }

    @Test
    fun `Part 2 - Example (Minimal)`() {
        assertThat(Day23(exampleMinimalInput).solvePart2()).isEqualTo(4)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day23(exampleInput).solvePart2()).isEqualTo(20)
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day23(puzzleInput).solvePart2()).isEqualTo(1055)
    }
}
