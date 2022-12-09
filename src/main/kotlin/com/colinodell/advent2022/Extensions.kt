package com.colinodell.advent2022

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
