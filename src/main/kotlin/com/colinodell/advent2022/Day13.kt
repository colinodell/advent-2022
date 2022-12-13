package com.colinodell.advent2022

class Day13(input: List<String>) {
    private val packets = input.filter { it.isNotBlank() }.map { Packet(it) }

    private val dividerPackets = listOf(
        Packet("[[2]]"),
        Packet("[[6]]")
    )

    fun solvePart1() = packets
        .chunked(2)
        .withIndex()
        .sumOf { (index, pair) ->
            if (pair.first() < pair.last()) index + 1 else 0
        }

    fun solvePart2() = packets
        .toMutableList()
        .also { it.addAll(dividerPackets) }
        .sorted()
        // Find the indices of the divider packets and add 1 to them
        .mapIndexed { i, p ->
            if (dividerPackets.contains(p)) i + 1 else 0
        }
        .filter { it > 0 }
        .reduce(Int::times)

    private class Packet(contents: String) : Comparable<Packet> {
        private val children: MutableList<Packet> = mutableListOf()
        private val value: Int?

        init {
            if (contents.containsInt()) {
                value = contents.toInt()
            } else {
                // This is a list of other packets
                value = null
                // Remove left and right brackets
                val trimmed = contents.substring(1, contents.length - 1)
                var depth = 0
                var tmp = ""
                for (c in trimmed.toCharArray()) {
                    if (depth == 0 && c == ',') {
                        // Create a new packet with everything up to this point
                        children.add(Packet(tmp))
                        tmp = ""
                    } else {
                        // Continue reading chars, keeping track of our depth as we move into and out of lists
                        depth += if (c == '[') 1 else if (c == ']') -1 else 0
                        tmp += c
                    }
                }
                // Add any remaining chars
                if (tmp.isNotEmpty()) {
                    children.add(Packet(tmp))
                }
            }
        }

        override operator fun compareTo(other: Packet): Int {
            return when {
                (value != null && other.value != null) -> value - other.value
                (value == null && other.value == null) -> compareChildren(other)
                else -> coerceToList().compareTo(other.coerceToList())
            }
        }

        private fun compareChildren(other: Packet): Int {
            for (i in 0 until children.size.coerceAtMost(other.children.size)) {
                val diff = children[i].compareTo(other.children[i])
                if (diff != 0) {
                    return diff
                }
            }
            return children.size - other.children.size
        }

        private fun coerceToList() = if (value != null) Packet("[$value]") else this
    }
}
