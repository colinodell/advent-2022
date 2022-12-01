package com.colinodell.advent2022

class Day01(private val input: String) {
    fun solvePart1(): Int {
        return input
            .split("\n\n")
            .map { it.split("\n").map { it.toInt() } }
            .maxOf { it.sum() }
    }

    fun solvePart2(): Int {
        return input
            .split("\n\n")
            .map { it.split("\n").map { it.toInt() } }
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }
}
