package com.colinodell.advent2022

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExtensionsTest {
    @Nested
    inner class StopOnceTests {
        @Test
        fun `returns empty sequence given an empty input`() {
            Assertions.assertThat(sequenceOf<Int>().stopOnce { false }.toList()).isEmpty()
        }

        @Test
        fun `returns all item if predicate always returns false`() {
            Assertions.assertThat(sequenceOf(1, 2, 3).stopOnce { false }.toList()).containsExactly(1, 2, 3)
        }

        @Test
        fun `returns all items through the first match`() {
            Assertions.assertThat(sequenceOf(1, 2, 3).stopOnce { it == 2 }.toList()).containsExactly(1, 2)
        }
    }
}
