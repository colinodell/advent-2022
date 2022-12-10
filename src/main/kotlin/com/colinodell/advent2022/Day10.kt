package com.colinodell.advent2022

import kotlin.math.abs

class Day10(input: List<String>) {
    private val registerValues = input
        .flatMap {
            val parts = it.split(" ")
            if (parts[0] == "noop") {
                listOf(0)
            } else {
                listOf(0, parts[1].toInt())
            }
        }
        .scan(1, Int::plus)
        .dropLast(1)

    fun solvePart1() = registerValues
        .mapIndexed { cycle, x ->
            if ((cycle + 1) % 40 == 20) {
                (cycle + 1) * x
            } else {
                0
            }
        }
        .sum()

    fun solvePart2() = registerValues
        .mapIndexed { cycle, x -> abs((cycle % 40) - x) < 2 } // Should the pixel be lit?
        .fold("") { crt, lit -> crt + if (lit) "█" else " " } // Draw the pixel
        .chunked(40)
        .joinToString("\n")
}
