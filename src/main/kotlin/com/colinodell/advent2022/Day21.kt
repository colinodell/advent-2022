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

    private val root = monkeys["root"]!! as JobMonkey

    fun solvePart1() = root.yell()

    fun solvePart2(): Long {
        val targetName = "humn"
        val pathToTarget = mutableListOf<Monkey>().also { root.find(targetName, it) }

        return when {
            (root.left in pathToTarget) -> (root.left as JobMonkey).findNumberGiving(root.right.yell(), targetName, pathToTarget)
            (root.right in pathToTarget) -> (root.right as JobMonkey).findNumberGiving(root.left.yell(), targetName, pathToTarget)
            else -> error("No path to human")
        }
    }

    abstract class Monkey {
        open lateinit var name: String
        abstract fun yell(): Long

        fun find(name: String, path: MutableList<Monkey>): Monkey? {
            if (this.name == name) {
                path.add(this)
                return this
            }

            if (this is JobMonkey) {
                left.find(name, path)?.let { path.add(this); return it }
                right.find(name, path)?.let { path.add(this); return it }
            }

            return null
        }

        fun findNumberGiving(result: Long, targetName: String, pathToTarget: List<Monkey>): Long {
            if (name == targetName) {
                return result
            }

            this as JobMonkey
            return when {
                left in pathToTarget -> left.findNumberGiving(invertLeft(right.yell(), result), targetName, pathToTarget)
                right in pathToTarget -> right.findNumberGiving(invertRight(left.yell(), result), targetName, pathToTarget)
                else -> throw IllegalStateException("Couldn't find $targetName in $left or $right")
            }
        }
    }

    private class NumberMonkey(
        override var name: String,
        private val value: Long
    ) : Monkey() {
        override fun yell() = value
    }

    private class JobMonkey(
        override var name: String,
        private val leftName: String,
        private val rightName: String,
        private val operator: String
    ) : Monkey() {
        lateinit var left: Monkey
        lateinit var right: Monkey

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

        fun invertLeft(right: Long, result: Long) = when (operator) {
            "+" -> result - right
            "-" -> result + right
            "*" -> result / right
            "/" -> result * right
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }

        fun invertRight(left: Long, result: Long) = when (operator) {
            "+" -> result - left
            "-" -> left - result
            "*" -> result / left
            "/" -> left / result
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }
}
