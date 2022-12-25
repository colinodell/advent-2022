package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("Day 25: Full of Hot Air")
class Day25Test {
    private val exampleInput = inputAsListOfString("day25_example.txt")
    private val puzzleInput = inputAsListOfString("day25_input.txt")

    @Test
    fun `Part 1 - Example`() {
        assertThat(Day25(exampleInput).solvePart1()).isEqualTo("2=-1=0")
    }

    @Test
    fun `Part 1 - Actual`() {
        assertThat(Day25(puzzleInput).solvePart1()).isEqualTo("20=212=1-12=200=00-1")
    }

    @ParameterizedTest
    @MethodSource("decimalSnafu")
    fun `SNAFU to Decimal Conversion`(decimal: Long, snafu: String) {
        assertThat(Day25.snafuToDecimal(snafu)).isEqualTo(decimal)
    }

    @ParameterizedTest
    @MethodSource("decimalSnafu")
    fun `Decimal to SNAFU Conversion`(decimal: Long, snafu: String) {
        assertThat(Day25.decimalToSnafu(decimal)).isEqualTo(snafu)
    }

    private companion object {
        @JvmStatic
        fun decimalSnafu() = Stream.of(
            Arguments.of(0L, "0"),
            Arguments.of(1L, "1"),
            Arguments.of(2L, "2"),
            Arguments.of(3L, "1="),
            Arguments.of(4L, "1-"),
            Arguments.of(5L, "10"),
            Arguments.of(6L, "11"),
            Arguments.of(7L, "12"),
            Arguments.of(8L, "2="),
            Arguments.of(9L, "2-"),
            Arguments.of(10L, "20"),
            Arguments.of(11L, "21"),
            Arguments.of(12L, "22"),
            Arguments.of(13L, "1=="),
            Arguments.of(14L, "1=-"),
            Arguments.of(15L, "1=0"),
            Arguments.of(16L, "1=1"),
            Arguments.of(17L, "1=2"),
            Arguments.of(18L, "1-="),
            Arguments.of(19L, "1--"),
            Arguments.of(20L, "1-0"),
            Arguments.of(31L, "111"),
            Arguments.of(32L, "112"),
            Arguments.of(107L, "1-12"),
            Arguments.of(198L, "2=0="),
            Arguments.of(201L, "2=01"),
            Arguments.of(353L, "1=-1="),
            Arguments.of(906L, "12111"),
            Arguments.of(1257L, "20012"),
            Arguments.of(1747L, "1=-0-2"),
            Arguments.of(2022L, "1=11-2"),
            Arguments.of(4890, "2=-1=0"),
            Arguments.of(12345L, "1-0---0"),
            Arguments.of(314159265L, "1121-1110-1=0"),
        )
    }
}
