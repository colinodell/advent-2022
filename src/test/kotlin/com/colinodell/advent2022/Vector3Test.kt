package com.colinodell.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Vector3")
class Vector3Test {
    @Test
    fun `Constructor and Properties`() {
        val vector = Vector3(1, 2, 3)
        assertThat(vector.x).isEqualTo(1)
        assertThat(vector.y).isEqualTo(2)
        assertThat(vector.z).isEqualTo(3)
    }

    @Test
    fun `Addition`() {
        val vector = Vector3(1, 2, 3) + Vector3(4, 5, 6)
        assertThat(vector.x).isEqualTo(5)
        assertThat(vector.y).isEqualTo(7)
        assertThat(vector.z).isEqualTo(9)
    }

    @Test
    fun `Subtraction`() {
        val vector = Vector3(4, 6, 8) - Vector3(1, 2, 3)
        assertThat(vector.x).isEqualTo(3)
        assertThat(vector.y).isEqualTo(4)
        assertThat(vector.z).isEqualTo(5)
    }

    @Test
    fun `Neighbors`() {
        val neighbors = Vector3(1, 2, 3).neighbors()
        assertThat(neighbors).hasSize(6)
        assertThat(neighbors).contains(Vector3(0, 2, 3))
        assertThat(neighbors).contains(Vector3(2, 2, 3))
        assertThat(neighbors).contains(Vector3(1, 1, 3))
        assertThat(neighbors).contains(Vector3(1, 3, 3))
        assertThat(neighbors).contains(Vector3(1, 2, 2))
        assertThat(neighbors).contains(Vector3(1, 2, 4))
    }
}

@DisplayName("Cuboid")
class CuboidTest {
    @Test
    fun `Constructor and Properties`() {
        val start = Vector3(1, 2, 3)
        val end = Vector3(4, 5, 6)
        val cuboid = Cuboid(start, end)

        assertThat(cuboid.start).isEqualTo(start)
        assertThat(cuboid.end).isEqualTo(end)
    }

    @Test
    fun `Contains`() {
        val cuboid = Cuboid(Vector3(1, 2, 3), Vector3(4, 5, 6))
        assertThat(cuboid.contains(Vector3(1, 2, 3))).isTrue
        assertThat(cuboid.contains(Vector3(4, 5, 6))).isTrue
        assertThat(cuboid.contains(Vector3(2, 3, 4))).isTrue
        assertThat(cuboid.contains(Vector3(0, 2, 3))).isFalse
        assertThat(cuboid.contains(Vector3(1, 1, 3))).isFalse
        assertThat(cuboid.contains(Vector3(1, 2, 2))).isFalse
    }
}

@DisplayName("Vector3 List Extensions")
class Vector3ListExtensionsTest {
    @Test
    fun `Min`() {
        val list = listOf(Vector3(1, 5, 9), Vector3(2, 4, 6), Vector3(3, 0, 2))
        assertThat(list.min()).isEqualTo(Vector3(1, 0, 2))
    }

    @Test
    fun `Max`() {
        val list = listOf(Vector3(1, 5, 9), Vector3(2, 4, 6), Vector3(3, 0, 2))
        assertThat(list.max()).isEqualTo(Vector3(3, 5, 9))
    }
}
