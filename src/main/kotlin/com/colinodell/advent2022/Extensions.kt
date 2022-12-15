package com.colinodell.advent2022

import kotlin.math.max

fun <T> Iterable<T>.chunkedBy(separator: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, t ->
        if (separator(t)) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(t)
        }
        acc
    }

/**
 * Counts through the first matching element.
 */
fun <T> Sequence<T>.countUntil(predicate: (T) -> Boolean): Int {
    var count = 0
    for (t in this@countUntil) {
        count++
        if (predicate(t)) {
            break
        }
    }
    return count
}

fun <T> Iterable<T>.productOf(predicate: (T) -> Int) = fold(1) { acc, t -> acc * predicate(t) }

fun Int.clamp(min: Int, max: Int) = maxOf(min, minOf(max, this))

fun String.containsInt() = toIntOrNull() != null

/**
 * Returns a new list with the ranges condensed into the smallest possible set of ranges.
 *
 * For example, given the ranges [4..6, 1..3, 7..9], this will return [1..9].
 */
fun Iterable<IntRange>.simplify(): List<IntRange> {
    val sortedRanges = sortedBy { it.first }
    val nonOverlappingRanges = mutableListOf<IntRange>()

    for (range in sortedRanges) {
        if (nonOverlappingRanges.isEmpty()) {
            nonOverlappingRanges.add(range)
        } else {
            val lastRange = nonOverlappingRanges.last()
            if (lastRange.last >= range.first) {
                nonOverlappingRanges[nonOverlappingRanges.lastIndex] = lastRange.first..max(lastRange.last, range.last)
            } else {
                nonOverlappingRanges.add(range)
            }
        }
    }

    return nonOverlappingRanges
}

/**
 * Returns a new list with the ranges clamped to the given min and max values.
 *
 * Any ranges that are completely outside the min/max range will be excluded.
 */
fun Iterable<IntRange>.clamp(min: Int, max: Int) =
    filter { it.first <= max && it.last >= min }
        .map { it.first.coerceAtLeast(min)..it.last.coerceAtMost(max) }
