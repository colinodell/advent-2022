package com.colinodell.advent2022

import kotlin.math.abs
import kotlin.math.max

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)

    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)

    operator fun times(scale: Int) = Vector2(x * scale, y * scale)

    operator fun div(scale: Int) = Vector2(x / scale, y / scale)

    operator fun rem(n: Int) = Vector2(x % n, y % n)

    fun negativeSafeModulo(n: Int) = Vector2((x + n) % n, (y + n) % n)

    fun normalize() = Vector2(x.clamp(-1, 1), y.clamp(-1, 1))

    fun isTouching(other: Vector2) = abs(x - other.x) <= 1 && abs(y - other.y) <= 1

    fun manhattanDistanceTo(other: Vector2) = abs(x - other.x) + abs(y - other.y)

    override fun toString() = "($x, $y)"

    fun neighbors() = setOf(
        Vector2(x - 1, y),
        Vector2(x + 1, y),
        Vector2(x, y - 1),
        Vector2(x, y + 1)
    )

    fun neighborsIncludingDiagonals() = neighbors() + setOf(
        Vector2(x - 1, y - 1),
        Vector2(x - 1, y + 1),
        Vector2(x + 1, y - 1),
        Vector2(x + 1, y + 1)
    )
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    enum class Rotation {
        LEFT, RIGHT
    }

    fun vector() = when (this) {
        UP -> Vector2(0, -1)
        DOWN -> Vector2(0, 1)
        LEFT -> Vector2(-1, 0)
        RIGHT -> Vector2(1, 0)
    }

    fun turn(rotation: Rotation) = when (rotation) {
        Rotation.LEFT -> turnLeft()
        Rotation.RIGHT -> turnRight()
    }

    fun turnLeft() = when (this) {
        UP -> LEFT
        DOWN -> RIGHT
        LEFT -> DOWN
        RIGHT -> UP
    }

    fun turnRight() = when (this) {
        UP -> RIGHT
        DOWN -> LEFT
        LEFT -> UP
        RIGHT -> DOWN
    }

    fun opposite() = when (this) {
        UP -> DOWN
        DOWN -> UP
        LEFT -> RIGHT
        RIGHT -> LEFT
    }

    companion object {
        fun from(c: Char): Direction = when (c) {
            '^' -> UP
            'v' -> DOWN
            '<' -> LEFT
            '>' -> RIGHT
            else -> throw IllegalArgumentException("Unknown direction: $c")
        }
    }
}

// A line that is at some multiple of 45 degrees (horizontal, vertical, or diagonal)
data class Line(val start: Vector2, val end: Vector2) {
    val points: List<Vector2> by lazy {
        val xDiff = end.x - start.x
        val yDiff = end.y - start.y
        val stepCount = max(abs(xDiff), abs(yDiff))

        val xStep = xDiff / stepCount
        val yStep = yDiff / stepCount

        (0..stepCount).map { Vector2(start.x + it * xStep, start.y + it * yStep) }
    }
}

data class Region(val topLeft: Vector2, val bottomRight: Vector2) {
    operator fun contains(point: Vector2): Boolean = point.x in topLeft.x..bottomRight.x && point.y in topLeft.y..bottomRight.y

    fun contract(amount: Int) = Region(topLeft + Vector2(amount, amount), bottomRight - Vector2(amount, amount))
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

fun <T> Grid<T>.neighborsOf(point: Vector2): Map<Vector2, T> {
    return point.neighbors().filter { containsKey(it) }.associateWith { get(it)!! }
}

fun <T> Grid<T>.topLeft() = Vector2(keys.minOf { it.x }, keys.minOf { it.y })
fun <T> Grid<T>.bottomRight() = Vector2(keys.maxOf { it.x }, keys.maxOf { it.y })
fun <T> Grid<T>.region() = Region(topLeft(), bottomRight())

fun Collection<Vector2>.width() = maxOf { it.x } - minOf { it.x } + 1
fun Collection<Vector2>.height() = maxOf { it.y } - minOf { it.y } + 1

fun Collection<Vector2>.toStringVisualization(): String {
    val minX = minOf { it.x }
    val minY = minOf { it.y }
    val maxX = maxOf { it.x }
    val maxY = maxOf { it.y }

    val grid = Array(maxY - minY + 1) { Array(maxX - minX + 1) { '.' } }

    for (point in this) {
        grid[point.y - minY][point.x - minX] = '#'
    }

    return grid.map { it.joinToString("") }.joinToString("\n")
}

fun <T> List<String>.toGrid(transform: (Char) -> T) = mutableMapOf<Vector2, T>().apply {
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            put(Vector2(x, y), transform(c))
        }
    }
}

fun List<String>.toGrid() = toGrid { it }

fun <T> Iterable<Vector2>.toGrid(valueGenerator: (Vector2) -> T) = mutableMapOf<Vector2, T>().apply {
    forEachIndexed { _, v ->
        put(v, valueGenerator(v))
    }
}
