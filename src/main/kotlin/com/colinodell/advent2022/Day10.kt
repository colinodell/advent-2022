package com.colinodell.advent2022

import kotlin.math.abs

class Day10(input: String) {
    private val registerValues = input
        .split(" ", "\n")
        .map { it.toIntOrNull() ?: 0 }
        .scan(1, Int::plus)
        .dropLast(1)

    fun solvePart1() = registerValues
        .let { listOf(0) + it } // Add a 0 at the beginning so everything is 1-indexed
        .filterIndexed { cycle, _ -> cycle % 40 == 20 } // Only take the cycles we're interested in
        .mapIndexed { i, x -> x * (i * 40 + 20) } // Calculate the signal strength
        .sum()

    fun solvePart2() = registerValues
        .mapIndexed { cycle, x -> abs((cycle % 40) - x) < 2 } // Should the pixel be lit?
        .fold("") { crt, lit -> crt + if (lit) "█" else " " } // Draw the pixel
        .chunked(40)
        .joinToString("\n")
}
