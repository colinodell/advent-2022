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
