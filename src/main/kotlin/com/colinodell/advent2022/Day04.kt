package com.colinodell.advent2022

class Day04(input: List<String>) {
    private val pairs = input
        .flatMap { it.split(",") }
        .map { it.split("-").map { it.toInt() } }
        .map { Range(it[0], it[1]) }
        .chunked(2)

    fun solvePart1() = pairs.count { it[0].fullyContains(it[1]) || it[1].fullyContains(it[0]) }

    fun solvePart2() = pairs.count { it[0].overlaps(it[1]) || it[1].overlaps(it[0]) }

    private data class Range(val start: Int, val end: Int) {
        fun fullyContains(other: Range) = start <= other.start && end >= other.end
        fun overlaps(other: Range) = start <= other.end && end >= other.start
    }
}
