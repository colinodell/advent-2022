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
                    File(parts[1], parts[0].toInt(), currentDir)
                }
            }
        }
    }

    fun solvePart1() = iterateFilesystem(root)
        .filterIsInstance<Directory>()
        .filter { it.size <= 100000 }
        .sumOf { it.size }

    fun solvePart2(): Int {
        val freeSpace = 70000000 - root.size
        val neededSpace = 30000000

        return iterateFilesystem(root)
            .filterIsInstance<Directory>()
            .filter { freeSpace + it.size >= neededSpace }
            .sortedBy { it.size }
            .first()
            .size
    }

    interface FilesystemObject {
        val name: String
        val size: Int
        val parent: Directory?
    }

    class File(override val name: String, override val size: Int, override val parent: Directory) : FilesystemObject {
        init {
            parent.children[name] = this
        }
    }

    class Directory(override val name: String, override val parent: Directory?) : FilesystemObject {
        init {
            parent?.children?.set(name, this)
        }

        val children = mutableMapOf<String, FilesystemObject>()

        override val size: Int
            get() = children.values.sumOf { it.size }
    }

    private fun iterateFilesystem(root: FilesystemObject): Sequence<FilesystemObject> {
        // Recursively iterate the filesystem
        return sequence {
            yield(root)

            if (root is Directory) {
                for (child in root.children.values) {
                    yieldAll(iterateFilesystem(child))
                }
            }
        }
    }
}
