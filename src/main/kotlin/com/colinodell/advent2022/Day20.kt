package com.colinodell.advent2022

class Day20(private val input: List<Int>) {
    fun solvePart1() = solve()

    fun solvePart2() = solve(10, 811589153)

    private fun solve(mixCount: Int = 1, decryptionKey: Int = 1) = input
        .map { it.toLong() * decryptionKey }
        .mapIndexed { index, value -> Number(index, value) }
        .toMutableList()
        .let { l -> mix(l, mixCount) }
        .let { l ->
            val zeroIndex = l.indexOfFirst { it.value == 0L }
            fun valueAtOffset(index: Int) = l[(zeroIndex + index) % l.size].value
            valueAtOffset(1000) + valueAtOffset(2000) + valueAtOffset(3000)
        }

    private data class Number(val originalIndex: Int, val value: Long) {
        override fun toString() = "($originalIndex, $value)"
    }

    private fun mix(originalList: List<Number>, times: Int): List<Number> {
        val list = originalList.toMutableList()

        repeat(times) {
            for (n in originalList) {
                val index = list.indexOf(n)
                list.removeAt(index)

                val newIndex = when {
                    (index + n.value) < 0 -> ((index + n.value) % list.size + list.size) % list.size
                    else -> (index + n.value) % list.size
                }.toInt()
                list.add(newIndex, n)
            }
        }

        return list
    }
}
