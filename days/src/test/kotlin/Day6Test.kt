package day6

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test

internal class Day6Test {

    private val testInput = """Time:      7  15   30
    Distance:  9  40  200""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("288", task1(testInput))
        assertEquals("4811940", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("71503", task2(testInput))
        assertEquals("30077773", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day6")
    }
}