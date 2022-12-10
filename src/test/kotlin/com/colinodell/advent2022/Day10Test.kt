package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Day 10: Cathode-Ray Tube")
class Day10Test {
    private val exampleInput = inputAsListOfString("day10_example.txt")
    private val puzzleInput = inputAsListOfString("day10_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day10(exampleInput).solvePart1()).isEqualTo(13140)
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day10(puzzleInput).solvePart1()).isEqualTo(14360)
    }

    @Test
    fun `Part 2 - Example`() {
        assertThat(Day10(exampleInput).solvePart2()).isEqualTo(
            """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
            """.trimIndent()
        )
    }

    @Test
    fun `Part 2 - Actual`() {
        assertThat(Day10(puzzleInput).solvePart2()).isEqualTo(
            """
            ###...##..#..#..##..####.###..####.####.
            #..#.#..#.#.#..#..#.#....#..#.#.......#.
            ###..#....##...#..#.###..#..#.###....#..
            #..#.#.##.#.#..####.#....###..#.....#...
            #..#.#..#.#.#..#..#.#....#.#..#....#....
            ###...###.#..#.#..#.####.#..#.####.####.
            """.trimIndent()
        )
    }
}
