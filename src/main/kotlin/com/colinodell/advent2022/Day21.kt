package com.colinodell.advent2022

class Day21(input: List<String>) {
    private val monkeys = input
        .map { line ->
            val parts = line.split(":? ".toRegex())
            if (parts.size == 2) {
                NumberMonkey(parts[0], parts[1].toLong())
            } else {
                JobMonkey(parts[0], parts[1], parts[3], parts[2])
            }
        }
        .associateBy { it.name }
        .apply {
            values.forEach { m ->
                if (m is JobMonkey) {
                    m.associate(this)
                }
            }
        }

    fun solvePart1() = monkeys["root"]!!.yell()


    private interface Monkey {
        fun yell(): Long
    }

    private class NumberMonkey(private val value: Long) : Monkey {
        override fun yell() = value
    }

    private class JobMonkey(private val leftName: String, private val rightName: String, private val operator: String) : Monkey {
        private lateinit var left: Monkey
        private lateinit var right: Monkey

        fun associate(monkeys: Map<String, Monkey>) {
            left = monkeys[leftName]!!
            right = monkeys[rightName]!!
        }

        override fun yell() = when (operator) {
            "+" -> left.yell() + right.yell()
            "-" -> left.yell() - right.yell()
            "*" -> left.yell() * right.yell()
            "/" -> left.yell() / right.yell()
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }
}
