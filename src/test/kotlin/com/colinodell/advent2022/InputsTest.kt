package com.colinodell.advent2022

import com.colinodell.advent2022.Inputs.inputAsListOfString
import com.colinodell.advent2022.Inputs.inputAsText
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class InputsTest {
    @Nested
    inner class InputAsTextTests {
        @Test
        fun `reads file as-is into a String`() {
            assertThat(inputAsText("read_file_test.txt"))
                .isEqualTo("1\n2\n3")
        }

        @Test
        fun `throws exception when file does not exist`() {
            assertThatThrownBy {
                inputAsText("this_does_not_exist.txt")
            }.isInstanceOf(IllegalArgumentException::class.java)
        }
    }

    @Nested
    inner class InputAsListTests {
        @Test
        fun `reads lines as Strings`() {
            assertThat(inputAsListOfString("read_file_test.txt"))
                .hasSize(3)
                .containsExactly("1", "2", "3")
        }

        @Test
        fun `throws exception when file does not exist`() {
            assertThatThrownBy {
                inputAsListOfString("this_does_not_exist.txt")
            }.isInstanceOf(IllegalArgumentException::class.java)
        }
    }
}
