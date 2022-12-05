package com.colinodell.advent2022

class Day01(input: List<String>) {
    private val elfCalories = input
        .chunkedBy { it.isEmpty() }
        .map { it.sumOf { it.toInt() } }

    fun solvePart1() = elfCalories.max()

    fun solvePart2() = elfCalories
        .sortedDescending()
        .take(3)
        .sum()
}
