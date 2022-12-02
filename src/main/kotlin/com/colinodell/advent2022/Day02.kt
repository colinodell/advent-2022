package com.colinodell.advent2022

class Day02(input: List<String>) {
    private val strategyGuide = input.map { Pair(it[0], it[2]) }

    fun solvePart1(): Int {
        return strategyGuide
            .map { Pair(Shape.from(it.first), Shape.from(it.second)) }
            .sumOf { score(it.first.outcomeWhenIPlay(it.second), it.second) }
    }

    fun solvePart2(): Int {
        return strategyGuide
            .map { Pair(Shape.from(it.first), Outcome.from(it.second)) }
            .map { Pair(it.first, it.first.respondWithShapeTo(it.second)) }
            .sumOf { score(it.first.outcomeWhenIPlay(it.second), it.second) }
    }

    private fun score(outcome: Outcome, shape: Shape): Int {
        return when (outcome) {
            Outcome.WIN -> 6
            Outcome.DRAW -> 3
            Outcome.LOSE -> 0
        } + when (shape) {
            Shape.ROCK -> 1
            Shape.PAPER -> 2
            Shape.SCISSORS -> 3
        }
    }

    enum class Outcome {
        WIN, LOSE, DRAW;

        companion object {
            fun from(c: Char): Outcome {
                return when (c) {
                    'X' -> LOSE
                    'Y' -> DRAW
                    'Z' -> WIN
                    else -> throw IllegalArgumentException("Invalid outcome: $c")
                }
            }
        }
    }

    enum class Shape {
        ROCK, PAPER, SCISSORS;

        private fun beats(other: Shape): Boolean {
            return when (this) {
                ROCK -> other == SCISSORS
                PAPER -> other == ROCK
                SCISSORS -> other == PAPER
            }
        }

        fun outcomeWhenIPlay(myShape: Shape): Outcome {
            return when {
                this == myShape -> Outcome.DRAW
                this.beats(myShape) -> Outcome.LOSE
                else -> Outcome.WIN
            }
        }

        fun respondWithShapeTo(desiredOutcome: Outcome) = when (desiredOutcome) {
            Outcome.WIN -> when (this) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
            Outcome.LOSE -> when (this) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }
            Outcome.DRAW -> this
        }

        companion object {
            fun from(c: Char): Shape {
                return when (c) {
                    'A', 'X' -> ROCK
                    'B', 'Y' -> PAPER
                    'C', 'Z' -> SCISSORS
                    else -> throw IllegalArgumentException("Invalid shape: $c")
                }
            }
        }
    }
}
