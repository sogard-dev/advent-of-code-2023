package day9

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test

private val testInput = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45""".lines()

internal class Day9Test {

    @Test
    fun testTaskOne() {
        assertEquals("114", task1(testInput))
        assertEquals("1581679977", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("5", task2("10 13 16 21 30 45".lines()))
        assertEquals("889", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day9")
    }
}