package com.colinodell.advent2022

import kotlin.math.max
import kotlin.math.min

class Day16(input: List<String>) {
    private val regex = Regex("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)")
    private val allValves = input.map {
        regex.find(it)!!.destructured.let { (name, flowRate, tunnels) ->
            Valve(name, flowRate.toInt(), tunnels.split(", "))
        }
    }

    // The valves we really want to visit
    private val goalValves = allValves.filter { it.rate > 0 || it.name == "AA" }

    // Pre-computed matrix of distances between all valves
    private val graph = calculateDistanceMatrix(allValves)
    private fun timeBetween(a: Valve, b: Valve) = graph[allValves.indexOf(a)][allValves.indexOf(b)]

    fun solvePart1() = solve(30).values.max()

    fun solvePart2(): Int {
        // Find the optimal solutions we can do ourselves in 26 minutes
        val bestPaths = solve(26)
        val bestFlows = mutableMapOf<Int, Int>().withDefault { 0 }
        for ((candidate, flow) in bestPaths) {
            bestFlows[candidate.visited] = max(bestFlows.getValue(candidate.visited), flow)
        }

        var highestFlow = 0
        // For each possible set of valves we could visit...
        for (mask in 0 until (1 shl goalValves.size)) {
            // Invert/flip all bits to find what the elephant should visit
            val elephantVisited = ((1 shl goalValves.size) - 1) xor mask
            // What's the best we could do here?
            highestFlow = max(highestFlow, bestFlows.getValue(elephantVisited))
            var weVisited = mask
            while (weVisited > 0) {
                highestFlow = max(highestFlow, bestFlows.getValue(elephantVisited) + bestFlows.getValue(weVisited))
                weVisited = (weVisited - 1) and mask
            }
        }

        return highestFlow
    }

    private data class Path(val goalValveIndex: Int, val visited: Int, val timeRemaining: Int)
    private data class SearchState(val goalValveIndex: Int, val visited: Int, val timeRemaining: Int, val totalFlow: Int)

    private fun solve(maxTime: Int): Map<Path, Int> {
        val queue = ArrayDeque<SearchState>()
        val bestFlowsByPath = mutableMapOf<Path, Int>().withDefault { -1 }

        fun enqueue(state: SearchState) {
            val candidate = Path(state.goalValveIndex, state.visited, state.timeRemaining)
            if (state.timeRemaining >= 0 && bestFlowsByPath.getValue(candidate) < state.totalFlow) {
                bestFlowsByPath[candidate] = state.totalFlow
                queue.add(state)
            }
        }

        enqueue(SearchState(goalValves.indexOfFirst { it.name == "AA" }, 0, maxTime, 0))

        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()

            // If we haven't visited this valve yet and time remains, maybe we can visit it
            if ((state.visited and (1 shl state.goalValveIndex) == 0) && state.timeRemaining > 0) {
                val flowHere = (state.timeRemaining - 1) * goalValves[state.goalValveIndex].rate
                enqueue(SearchState(state.goalValveIndex, state.visited or (1 shl state.goalValveIndex), state.timeRemaining - 1, state.totalFlow + flowHere))
            }

            for (next in goalValves) {
                val travelTime = timeBetween(goalValves[state.goalValveIndex], next)
                if (travelTime <= state.timeRemaining) {
                    enqueue(SearchState(goalValves.indexOf(next), state.visited, state.timeRemaining - travelTime, state.totalFlow))
                }
            }
        }

        return bestFlowsByPath
    }

    private fun calculateDistanceMatrix(nodes: List<Valve>): Array<Array<Int>> {
        val maximum = Int.MAX_VALUE / 10
        val distances = Array(nodes.size) { Array(nodes.size) { maximum } }

        // Set the distance from a node to itself to 0
        for (i in nodes.indices) {
            distances[i][i] = 0
        }

        // Set the distance between adjacent nodes to 1
        for (i in nodes.indices) {
            for (adjacent in nodes[i].connectsTo) {
                val j = nodes.indexOfFirst { it.name == adjacent }
                distances[i][j] = min(distances[i][j], 1)
            }
        }

        // Run the Floyd–Warshall algorithm
        for (i in nodes.indices) {
            for (j in nodes.indices) {
                for (k in nodes.indices) {
                    distances[j][k] = min(distances[j][k], distances[j][i] + distances[i][k])
                }
            }
        }

        return distances
    }

    private data class Valve(val name: String, val rate: Int, val connectsTo: List<String>)
}
