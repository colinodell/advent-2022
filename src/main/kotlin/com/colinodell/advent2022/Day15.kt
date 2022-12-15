package com.colinodell.advent2022

import kotlin.math.abs

class Day15(input: List<String>) {
    private val regex = Regex("x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")

    private val sensors = input.map { line ->
        regex.find(line)!!.destructured.let { (x, y, bx, by) ->
            val sensor = Vector2(x.toInt(), y.toInt())
            val beacon = Vector2(bx.toInt(), by.toInt())
            Sensor(sensor, beacon)
        }
    }

    fun solvePart1(row: Int) = findDisallowedPositions(row).sumOf { it.count() } - 1

    fun solvePart2(max: Int): Long {
        for (row in 0..max) {
            val disallowedRanges = findDisallowedPositions(row).clamp(0, 4000000)

            // Assume the distress beacon is not coming from the very edges of our search area
            // With this assumption, we can simply check if the range has a gap somewhere in the middle
            if (disallowedRanges.size == 1) {
                continue
            }

            // Gap found! Assume there's only one gap, so the x coordinate is just 1 higher than the first range
            val x = disallowedRanges[0].last + 1

            return x.toLong() * 4000000L + row.toLong()
        }

        error("No solution found")
    }

    private fun findDisallowedPositions(row: Int): List<IntRange> {
        val disallowedRanges = mutableSetOf<IntRange>()
        for (sensor in sensors) {
            val dy = abs(row - sensor.position.y)
            if (dy > sensor.radius) {
                continue
            }
            val dx = sensor.radius - dy
            disallowedRanges.add(-dx + sensor.position.x..dx + sensor.position.x)
        }

        return disallowedRanges.simplify()
    }

    private class Sensor(val position: Vector2, val nearestBeacon: Vector2) {
        val radius: Int by lazy {
            position.manhattanDistanceTo(nearestBeacon)
        }
    }
}
