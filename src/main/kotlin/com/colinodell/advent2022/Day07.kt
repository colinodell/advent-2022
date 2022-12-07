package com.colinodell.advent2022

class Day07(input: List<String>) {
    private val root = Directory("/", null)

    init {
        var currentDir = root
        // Assume the first line is the root, so skip it
        for (line in input.drop(1)) {
            val parts = line.split(" ")
            if (line == "$ cd ..") {
                currentDir = currentDir.parent!!
            } else if (parts[0] == "$" && parts[1] == "cd") {
                currentDir = currentDir.children[parts[2]] as Directory
            } else if (parts[0] == "$" && parts[1] == "ls") {
                // no-op
            } else if (parts.size == 2 && parts[0] == "dir") {
                Directory(parts[1], currentDir)
            } else if (parts.size == 2) {
                currentDir.fileSize += parts[0].toInt()
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

    class Directory(private val name: String, val parent: Directory?) {
        val children = mutableMapOf<String, Directory>()
        var fileSize: Int = 0

        init {
            parent?.children?.set(name, this)
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
