package com.colinodell.advent2021

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)

    override fun toString() = "($x, $y)"
}

typealias Grid<T> = Map<Vector2, T>
data class GridEntry<V>(override val key: Vector2, override val value: V?) : Map.Entry<Vector2, V?>

fun <T> Grid<T>.pointsToThe(direction: Vector2, source: Vector2) = sequence {
    var current = source + direction
    while (current in keys) {
        yield(GridEntry(current, getValue(current)))
        current += direction
    }
}

fun <T> List<String>.toGrid(transform: (Char) -> T) = mutableMapOf<Vector2, T>().apply {
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            put(Vector2(x, y), transform(c))
        }
    }
}
