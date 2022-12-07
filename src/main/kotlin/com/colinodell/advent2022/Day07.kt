package com.colinodell.advent2022

class Day07(input: List<String>) {
    private val root = Directory()

    init {
        var currentDir = root
        // Assume the first line is the root, so skip it
        for (line in input.drop(1)) {
            val parts = line.split(" ")
            when (true) {
                (line == "$ cd ..") -> currentDir = currentDir.parent!!
                (parts[1] == "cd") -> currentDir = currentDir.children[parts[2]]!!
                (parts[1] == "ls") -> null // no-op
                (parts[0] == "dir") -> currentDir.addChild(parts[1])
                (parts.size == 2) -> currentDir.fileSize += parts[0].toInt()
                else -> throw IllegalArgumentException("Unknown operation: $line")
            }
        }
    }

    fun solvePart1() = iterateDirectories(root)
        .filter { it.totalSize() <= 100000 }
        .sumOf { it.totalSize() }

    fun solvePart2(): Int {
        val freeSpace = 70000000 - root.totalSize()
        val neededSpace = 30000000

        return iterateDirectories(root)
            .filter { freeSpace + it.totalSize() >= neededSpace }
            .sortedBy { it.totalSize() }
            .first()
            .totalSize()
    }

    class Directory(val parent: Directory? = null) {
        val children = mutableMapOf<String, Directory>()
        var fileSize: Int = 0

        fun addChild(name: String) {
            children[name] = Directory(this)
        }

        fun totalSize(): Int = fileSize + children.values.sumOf { it.totalSize() }
    }

    private fun iterateDirectories(root: Directory): Sequence<Directory> {
        return sequence {
            yield(root)
            for (child in root.children.values) {
                yieldAll(iterateDirectories(child))
            }
        }
    }
}
