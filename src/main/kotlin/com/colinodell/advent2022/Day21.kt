package com.colinodell.advent2022

class Day21(input: List<String>) {
    private val monkeys = input.associate {
        val (name, job) = it.split(": ")
        name to Monkey(job)
    }

    fun solvePart1() = monkeys["root"]!!.yell(monkeys)

    private class Monkey(private var job: String) {
        private var finalValue: Long? = null

        fun yell(monkeys: Map<String, Monkey>): Long {
            if (finalValue != null) {
                return finalValue!!
            }

            val parts = job.split(" ")
            if (parts.size == 1) {
                finalValue = parts[0].toLong()
                return finalValue!!
            }

            val op = parts[1]
            val a = monkeys[parts[0]]!!.yell(monkeys)
            val b = monkeys[parts[2]]!!.yell(monkeys)

            finalValue = when (op) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> a / b
                else -> throw IllegalArgumentException("Unknown op: $op")
            }

            return finalValue!!
        }
    }
}
