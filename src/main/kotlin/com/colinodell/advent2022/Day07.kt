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
            } else if (parts.size == 2) {
                if (parts[0] == "dir") {
                    Directory(parts[1], currentDir)
                } else {
                    currentDir.addFile(parts[0].toInt())
                }
            }
        }
    }

    fun solvePart1() = iterateDirectories(root)
        .filter { it.fileSize() <= 100000 }
        .sumOf { it.fileSize() }

    fun solvePart2(): Int {
        val freeSpace = 70000000 - root.fileSize()
        val neededSpace = 30000000

        return iterateDirectories(root)
            .filter { freeSpace + it.fileSize() >= neededSpace }
            .sortedBy { it.fileSize() }
            .first()
            .fileSize()
    }

    class Directory(private val name: String, val parent: Directory?) {
        val children = mutableMapOf<String, Directory>()
        private var fileSize: Int = 0

        init {
            parent?.children?.set(name, this)
        }

        fun addFile(size: Int) {
            fileSize += size
        }

        fun fileSize(): Int = fileSize + children.values.sumOf { it.fileSize() }
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
