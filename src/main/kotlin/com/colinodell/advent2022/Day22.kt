package com.colinodell.advent2022

import kotlin.math.sqrt

class Day22(input: List<String>) {
    private val grid = input.dropLast(2).toGrid().filter { it.value != ' ' }.toMutableMap()
    private val pathToFollow = input.last().split("(?<=[A-Z])|(?=[A-Z])".toRegex())

    private val startingPosition = grid.minOf { it.key.y }.let { y ->
        Vector2(grid.filter { it.key.y == y }.minOf { it.key.x }, y)
    }

    private val faceSize = sqrt((grid.count() / 6).toDouble()).toInt()

    fun solvePart1() = solve(::wrapNaive)
    fun solvePart2() = solve(::wrapCube)

    private data class State(val pos: Vector2, val dir: Direction) {
        fun password() = (1000 * (pos.y + 1)) + (4 * (pos.x + 1)) + when (dir) {
            Direction.RIGHT -> 0
            Direction.DOWN -> 1
            Direction.LEFT -> 2
            Direction.UP -> 3
        }
    }

    private fun solve(wrap: (State) -> State): Int {
        var state = State(startingPosition, Direction.RIGHT)

        pathToFollow.forEach { step ->
            state = when (step) {
                "R" -> State(state.pos, state.dir.turnRight())
                "L" -> State(state.pos, state.dir.turnLeft())
                else -> move(step.toInt(), state, wrap)
            }
        }

        return state.password()
    }

    private fun move(distance: Int, state: State, wrap: (State) -> State): State {
        var state = state

        for (i in 1..distance) {
            var nextState = State(state.pos + state.dir.vector(), state.dir)
            var peek = grid[nextState.pos]

            // Did we hit the edge?
            if (peek == null) {
                nextState = wrap(state)
                peek = grid[nextState.pos]
            }

            if (peek == '#') {
                return state
            }

            state = nextState
        }

        return state
    }

    private fun wrapNaive(state: State): State {
        val newPosition = when (state.dir) {
            Direction.RIGHT -> Vector2(grid.filter { it.key.y == state.pos.y }.minOf { it.key.x }, state.pos.y)
            Direction.LEFT -> Vector2(grid.filter { it.key.y == state.pos.y }.maxOf { it.key.x }, state.pos.y)
            Direction.DOWN -> Vector2(state.pos.x, grid.filter { it.key.x == state.pos.x }.minOf { it.key.y })
            Direction.UP -> Vector2(state.pos.x, grid.filter { it.key.x == state.pos.x }.maxOf { it.key.y })
        }

        return State(newPosition, state.dir)
    }

    private fun wrapCube(state: State): State {
        val curFace = state.pos / faceSize
        val nextFace = faces[curFace]!![state.dir]!!
        var nextRelativePos = (state.pos + state.dir.vector()).negativeSafeModulo(faceSize)
        var newDir = state.dir

        while (faces[nextFace]!![newDir.opposite()] != curFace) {
            nextRelativePos = Vector2(faceSize - 1 - nextRelativePos.y, nextRelativePos.x)
            newDir = newDir.turnRight()
        }

        val newPosition = (nextFace * faceSize) + nextRelativePos

        return State(newPosition, newDir)
    }

    /**
     * Adapted from https://bit.ly/3WENzKQ
     */
    private val faces by lazy {
        // Generate initial adjacencies
        val queue = ArrayDeque<Vector2>().apply { add(startingPosition) }
        val visited = mutableMapOf<Vector2, MutableMap<Direction, Vector2>>()
        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            for (dir in Direction.values()) {
                val w = v + (dir.vector() * faceSize)
                if (w !in grid) continue
                if (w !in visited.keys) {
                    visited.getOrPut(v) { mutableMapOf() }[dir] = w
                    visited.getOrPut(w) { mutableMapOf() }[dir.opposite()] = v
                    queue.add(w)
                }
            }
        }

        // Normalize face-edge mapping
        val faces = visited
            .mapKeys { it.key / faceSize }
            .mapValues { it.value.mapValues { it.value / faceSize }.toMutableMap() }

        // Fill in missing edge data using corners
        while (faces.any { it.value.size < 4 }) {
            for (face in faces.keys) {
                for (dir in Direction.values()) {
                    if (faces[face]?.get(dir) != null) continue
                    for (rotation in Direction.Rotation.values()) {
                        val commonFace = faces[face]?.get(dir.turn(rotation)) ?: continue
                        val commonFaceEdge = faces[commonFace]?.entries?.firstOrNull { it.value == face }?.key ?: continue
                        val missingFace = faces[commonFace]?.get(commonFaceEdge.turn(rotation)) ?: continue
                        val missingFaceEdge = faces[missingFace]?.entries?.firstOrNull { it.value == commonFace }?.key ?: continue
                        faces[missingFace]!![missingFaceEdge.turn(rotation)] = face
                        faces[face]!![dir] = missingFace
                        break
                    }
                }
            }
        }

        faces
    }
}
