package com.colinodell.advent2022

class Day11(private val allMonkeys: List<Monkey>) {
    private val lcm = allMonkeys.map { it.divisorTest }.fold(1L, Long::times)

    fun solvePart1() = solve(20) { it.floorDiv(3) }
    fun solvePart2() = solve(10000) { it.mod(lcm) }

    private fun solve(times: Int, modifier: (Long) -> Long) =
        repeat(times) {
            allMonkeys.forEach { m -> m.inspectItems(allMonkeys, modifier) }
        }
            .let { allMonkeys }
            .map { m -> m.itemsInspected }
            .sortedDescending()
            .take(2)
            .let { it[0] * it[1] }
}

data class Monkey(
    val items: MutableList<Long>,
    private val operation: (Long) -> Long,
    val divisorTest: Long,
    private val nextMonkeyIfTrue: Int,
    private val nextMonkeyIfFalse: Int
) {
    var itemsInspected: Long = 0

    fun inspectItems(otherMonkeys: List<Monkey>, modifier: (Long) -> Long) {
        items
            .map { modifier(operation(it)) }
            .forEach { item ->
                val nextMonkey = if (item % divisorTest == 0L) nextMonkeyIfTrue else nextMonkeyIfFalse
                otherMonkeys[nextMonkey].items.add(item)
                itemsInspected++
            }
        items.clear()
    }
}
