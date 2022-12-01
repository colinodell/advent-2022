package com.colinodell.advent2022

fun <T> Iterable<T>.chunkedBy(separator: (T) -> Boolean): Sequence<List<T>> {
    return sequence {
        val chunk = mutableListOf<T>()
        for (item in this@chunkedBy) {
            if (separator(item)) {
                if (chunk.isNotEmpty()) {
                    yield(chunk.toList())
                    chunk.clear()
                }
            } else {
                chunk.add(item)
            }
        }
        if (chunk.isNotEmpty()) {
            yield(chunk)
        }
    }
}
