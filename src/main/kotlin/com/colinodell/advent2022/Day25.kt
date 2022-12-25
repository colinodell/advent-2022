package com.colinodell.advent2022

import kotlin.math.pow

class Day25(private val input: List<String>) {
    fun solvePart1() = input.sumOf { snafuToDecimal(it) }.toSnafu()

    companion object {
        private val digitMap = mapOf(
            '=' to -2L,
            '-' to -1L,
            '0' to 0L,
            '1' to 1L,
            '2' to 2L
        )

        private val reverseMap = digitMap.entries.associate { (k, v) -> v to k }

        fun snafuToDecimal(snafu: String) = snafu
            .toCharArray()
            .reversed()
            .withIndex()
            .fold(0L) { acc, (i: Int, c) ->
                acc + 5.0.pow(i.toDouble()).toLong() * digitMap[c]!!
            }

        fun decimalToSnafu(decimal: Long) = when (decimal) {
            0L -> "0"
            else -> decimal.toSnafu()
        }

        private fun Long.toSnafu(): String {
            if (this == 0L) return ""

            return when (val next = this % 5L) {
                0L, 1L, 2L -> (this / 5L).toSnafu() + reverseMap[next]
                3L, 4L -> (this / 5L + 1L).toSnafu() + reverseMap[next - 5L]
                else -> error("$this is not a valid snafu num")
            }
        }
    }
}
