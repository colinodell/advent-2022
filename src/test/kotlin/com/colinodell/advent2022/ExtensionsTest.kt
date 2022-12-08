package com.colinodell.advent2022

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExtensionsTest {
    @Nested
    inner class CountUntilTests {
        @Test
        fun `returns 0 given an empty input`() {
            Assertions.assertThat(sequenceOf<Int>().countUntil { false }).isEqualTo(0)
        }

        @Test
        fun `counts all items if predicate always returns false`() {
            Assertions.assertThat(sequenceOf(1, 2, 3).countUntil { false }).isEqualTo(3)
        }

        @Test
        fun `counts all items through the first match`() {
            Assertions.assertThat(sequenceOf(1, 2, 3).countUntil { it == 2 }).isEqualTo(2)
        }
    }

    @Nested
    inner class ProductOfTests {
        @Test
        fun `returns 1 given an empty input`() {
            Assertions.assertThat(listOf<Int>().productOf { it }).isEqualTo(1)
        }

        @Test
        fun `returns the product of all items`() {
            Assertions.assertThat(listOf(1, 2, 3).productOf { it }).isEqualTo(6)
        }
    }
}
