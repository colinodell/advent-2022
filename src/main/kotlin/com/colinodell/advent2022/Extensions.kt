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
 * Like takeWhile(), except that it stops AFTER the first matching element.
 */
fun <T> Sequence<T>.stopOnce(predicate: (T) -> Boolean) = sequence {
    for (t in this@stopOnce) {
        yield(t)
        if (predicate(t)) {
            break
        }
    }
}
