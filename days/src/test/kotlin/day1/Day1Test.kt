package day1

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test

internal class Day1Test {

    @Test
    fun testTaskOne() {
        assertEquals(
            "142", task1(
                """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet""".lines()
            )
        )
        assertEquals("54239", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals(
            "281", task2(
                """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen""".lines()
            )
        )
        assertEquals("55343", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day1")
    }
}