package com.colinodell.advent2022

import java.lang.Integer.min

class Day06(private val input: String) {
    fun solvePart1() = findMarker(4)
    fun solvePart2() = findMarker(14)

    private fun findMarker(markerSize: Int): Int {
        for (i in input.indices) {
            val possibleMarker = input.substring(i, min(input.length - 1, i + markerSize))
            if (possibleMarker.toSet().size == markerSize) {
                return i + markerSize
            }
        }

        error("No marker found!")
    }
}
