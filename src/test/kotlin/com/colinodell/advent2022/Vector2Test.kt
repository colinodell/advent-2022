package com.colinodell.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Vector2")
class Vector2Test {
    @Test
    fun `Constructor and Properties`() {
        val vector = Vector2(1, 2)
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(2)
    }

    @Test
    fun `Addition`() {
        val vector = Vector2(1, 2) + Vector2(3, 4)
        assertThat(vector.x).isEqualTo(4)
        assertThat(vector.y).isEqualTo(6)
    }

    @Test
    fun `Subtraction`() {
        val vector = Vector2(4, 6) - Vector2(3, 4)
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(2)
    }

    @Test
    fun `Multiplication`() {
        val vector = Vector2(1, -2) * 3
        assertThat(vector.x).isEqualTo(3)
        assertThat(vector.y).isEqualTo(-6)
    }

    @Test
    fun `Division`() {
        val vector = Vector2(6, -8) / 3
        assertThat(vector.x).isEqualTo(2)
        assertThat(vector.y).isEqualTo(-2)
    }

    @Test
    fun `Modulus`() {
        val vector = Vector2(4, -8) % 3
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(-2)
    }

    @Test
    fun `Negative Safe Modulus`() {
        val vector = Vector2(-1, 5).negativeSafeModulo(3)
        assertThat(vector.x).isEqualTo(2)
        assertThat(vector.y).isEqualTo(2)
    }

    @Test
    fun `String Representation`() {
        val vector = Vector2(1, 2)
        assertThat(vector.toString()).isEqualTo("(1, 2)")
    }

    @Test
    fun `Normalization to Unit Vectors`() {
        val vector = Vector2(35, -7)
        assertThat(vector.normalize()).isEqualTo(Vector2(1, -1))
    }

    @Test
    fun `Is Touching`() {
        // Same position
        assertThat(Vector2(0, 0).isTouching(Vector2(0, 0))).isTrue

        // Directly adjacent
        assertThat(Vector2(0, 0).isTouching(Vector2(-1, 0))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(1, 0))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(0, -1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(0, 1))).isTrue

        // Diagonally adjacent
        assertThat(Vector2(0, 0).isTouching(Vector2(-1, -1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(1, -1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(-1, 1))).isTrue
        assertThat(Vector2(0, 0).isTouching(Vector2(1, 1))).isTrue

        // Not touching
        assertThat(Vector2(0, 0).isTouching(Vector2(2, 0))).isFalse
        assertThat(Vector2(0, 0).isTouching(Vector2(0, 2))).isFalse
        assertThat(Vector2(0, 0).isTouching(Vector2(2, 2))).isFalse
    }

    @Test
    fun `Manhattan Distance`() {
        assertThat(Vector2(0, 0).manhattanDistanceTo(Vector2(0, 0))).isEqualTo(0)
        assertThat(Vector2(0, 0).manhattanDistanceTo(Vector2(3, 2))).isEqualTo(5)
        assertThat(Vector2(-1, -1).manhattanDistanceTo(Vector2(1, 1))).isEqualTo(4)
    }
}

@Nested
@DisplayName("Direction")
class DirectionTest {
    @Test
    fun `Turn`() {
        assertThat(Direction.UP.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.RIGHT)
        assertThat(Direction.UP.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.LEFT)
        assertThat(Direction.RIGHT.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.DOWN)
        assertThat(Direction.RIGHT.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.UP)
        assertThat(Direction.DOWN.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.LEFT)
        assertThat(Direction.DOWN.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.RIGHT)
        assertThat(Direction.LEFT.turn(Direction.Rotation.RIGHT)).isEqualTo(Direction.UP)
        assertThat(Direction.LEFT.turn(Direction.Rotation.LEFT)).isEqualTo(Direction.DOWN)
    }

    @Test
    fun `Turn Left`() {
        assertThat(Direction.UP.turnLeft()).isEqualTo(Direction.LEFT)
        assertThat(Direction.LEFT.turnLeft()).isEqualTo(Direction.DOWN)
        assertThat(Direction.DOWN.turnLeft()).isEqualTo(Direction.RIGHT)
        assertThat(Direction.RIGHT.turnLeft()).isEqualTo(Direction.UP)
    }

    @Test
    fun `Turn Right`() {
        assertThat(Direction.UP.turnRight()).isEqualTo(Direction.RIGHT)
        assertThat(Direction.RIGHT.turnRight()).isEqualTo(Direction.DOWN)
        assertThat(Direction.DOWN.turnRight()).isEqualTo(Direction.LEFT)
        assertThat(Direction.LEFT.turnRight()).isEqualTo(Direction.UP)
    }

    @Test
    fun `Vector`() {
        assertThat(Direction.UP.vector()).isEqualTo(Vector2(0, -1))
        assertThat(Direction.RIGHT.vector()).isEqualTo(Vector2(1, 0))
        assertThat(Direction.DOWN.vector()).isEqualTo(Vector2(0, 1))
        assertThat(Direction.LEFT.vector()).isEqualTo(Vector2(-1, 0))
    }

    @Test
    fun `Opposite`() {
        assertThat(Direction.UP.opposite()).isEqualTo(Direction.DOWN)
        assertThat(Direction.RIGHT.opposite()).isEqualTo(Direction.LEFT)
        assertThat(Direction.DOWN.opposite()).isEqualTo(Direction.UP)
        assertThat(Direction.LEFT.opposite()).isEqualTo(Direction.RIGHT)
    }
}

@Nested
@DisplayName("Line")
class LineTest {
    @Test
    fun `Example 1`() {
        val line = Line(Vector2(1, 1), Vector2(1, 3))
        assertThat(line.points).containsExactly(
            Vector2(1, 1),
            Vector2(1, 2),
            Vector2(1, 3),
        )
    }

    @Test
    fun `Example 2`() {
        val line = Line(Vector2(9, 7), Vector2(7, 7))
        assertThat(line.points).containsExactly(
            Vector2(9, 7),
            Vector2(8, 7),
            Vector2(7, 7),
        )
    }

    @Test
    fun `Example 3`() {
        val line = Line(Vector2(1, 1), Vector2(3, 3))
        assertThat(line.points).containsExactly(
            Vector2(1, 1),
            Vector2(2, 2),
            Vector2(3, 3),
        )
    }

    @Test
    fun `Example 4`() {
        val line = Line(Vector2(9, 7), Vector2(7, 9))
        assertThat(line.points).containsExactly(
            Vector2(9, 7),
            Vector2(8, 8),
            Vector2(7, 9),
        )
    }
}

@Nested
@DisplayName("Vector2 Collection")
class CollectionTest {
    private val collection: Set<Vector2> = setOf(
        Vector2(0, 0),
        Vector2(2, 0),
        Vector2(1, 1),
        Vector2(0, 2),
        Vector2(2, 2),
    )

    @Test
    fun `width()`() {
        assertThat(collection.width()).isEqualTo(3)
    }

    @Test
    fun `height()`() {
        assertThat(collection.height()).isEqualTo(3)
    }

    @Test
    fun `toStringVisualization()`() {
        assertThat(collection.toStringVisualization()).isEqualTo(
            """
            #.#
            .#.
            #.#
            """.trimIndent()
        )
    }
}
