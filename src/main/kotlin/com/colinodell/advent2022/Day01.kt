package com.colinodell.advent2022

class Day01(input: List<String>) {
    private val elfCalories = input
        .chunkedBy { it.isEmpty() }
        .map { it.sumOf { it.toInt() } }

    fun solvePart1(): Int {
        return elfCalories.max()
    }

    fun solvePart2(): Int {
        return elfCalories
            .sortedDescending()
            .take(3)
            .sum()
    }
}
