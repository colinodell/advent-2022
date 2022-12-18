package com.colinodell.advent2022

data class Vector3(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector3) = Vector3(x - other.x, y - other.y, z - other.z)

    fun neighbors() = listOf(
        Vector3(x - 1, y, z),
        Vector3(x + 1, y, z),
        Vector3(x, y - 1, z),
        Vector3(x, y + 1, z),
        Vector3(x, y, z - 1),
        Vector3(x, y, z + 1)
    )
}

data class Cuboid(val start: Vector3, val end: Vector3) {
    init {
        require(start.x <= end.x)
        require(start.y <= end.y)
        require(start.z <= end.z)
    }

    fun contains(vector: Vector3) = vector.x in start.x..end.x && vector.y in start.y..end.y && vector.z in start.z..end.z
}

fun Iterable<Vector3>.min() = Vector3(minOf { it.x }, minOf { it.y }, minOf { it.z })
fun Iterable<Vector3>.max() = Vector3(maxOf { it.x }, maxOf { it.y }, maxOf { it.z })
