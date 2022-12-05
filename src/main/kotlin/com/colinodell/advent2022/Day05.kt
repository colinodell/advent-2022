package com.colinodell.advent2022

class Day05(input: String) {
    private val stacks = mutableMapOf<Int, ArrayDeque<Char>>()
    private val moves: List<Move>

    init {
        val (rawStacks, rawCommands) = input.split("\n\n")

        for (row in rawStacks.split("\n").dropLast(1)) {
            row.chunked(4).forEachIndexed { index, crate ->
                stacks.computeIfAbsent(index + 1) { ArrayDeque() }
                if (crate.isNotBlank()) {
                    stacks[index + 1]!!.addFirst(crate[1])
                }
            }
        }

        val moveParser = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        moves = rawCommands.split("\n").map {
            val (_, count, source, destination) = moveParser.matchEntire(it)!!.groupValues
            Move(count.toInt(), source.toInt(), destination.toInt())
        }
    }

    fun solvePart1(): String {
        moves.forEach { move ->
            repeat(move.count) {
                // log to console
                stacks[move.destination]!!.addLast(stacks[move.source]!!.removeLast())
            }
        }

        return getStackTops()
    }

    private fun getStackTops(): String {
        return stacks.values.map { it.last() }.joinToString("")
    }

    private data class Move(val count: Int, val source: Int, val destination: Int)
}
