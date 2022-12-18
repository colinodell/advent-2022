package com.colinodell.advent2022

class Day18(input: List<String>) {
    private val cubes = input.map { line ->
        val (x, y, z) = line.split(",").map { it.toInt() }
        Vector3(x, y, z)
    }

    fun solvePart1() = cubes
        .flatMap { it.neighbors() }
        .filter { !cubes.contains(it) }
        .size

    fun solvePart2() = cubes
        .flatMap { it.neighbors() }
        .filter { floodFill.contains(it) }
        .size

    private val floodFill: Set<Vector3> by lazy {
        val searchSpace = Cuboid(
            cubes.min() - Vector3(1, 1, 1),
            cubes.max() + Vector3(1, 1, 1)
        )

        val seen = mutableSetOf<Vector3>()
        val queue = ArrayDeque<Vector3>()

        queue.add(searchSpace.start)

        while (queue.isNotEmpty()) {
            val node = queue.removeLast()
            queue.addAll((node.neighbors() - cubes - seen).filter { searchSpace.contains(it) })
            seen.add(node)
        }
        seen
    }
}
