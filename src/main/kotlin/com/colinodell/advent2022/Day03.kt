package com.colinodell.advent2022

class Day03(input: List<String>) {
    private val rucksacks = input.map { it.toList() }

    fun solvePart1() = rucksacks
        .map { it.chunked(it.size / 2) } // Split each line into two compartments
        .map { it[0].intersect(it[1]) } // Find the common item type
        .sumOf { priority(it.first()) } // Sum the priorities

    fun solvePart2() = rucksacks
        .chunked(3) // Divide rucksacks into groups of 3
        .map { it[0].intersect(it[1]).intersect(it[2]) } // Find the common item type in each group
        .sumOf { priority(it.first()) } // Sum the priorities

    private fun priority(c: Char): Int = if (c.isUpperCase()) c.code - 38 else c.code - 96
}
