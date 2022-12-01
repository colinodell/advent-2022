package com.colinodell.advent2022

class Day01(private val input: List<String>) {
    fun solvePart1(): Int {
        return input
            .chunkedBy { it.isEmpty() }
            .map { it.sumOf { it.toInt() } }
            .max()
    }

    fun solvePart2(): Int {
        return input
            .chunkedBy { it.isEmpty() }
            .map { it.sumOf { it.toInt() } }
            .sortedDescending()
            .take(3)
            .sum()
    }
}
