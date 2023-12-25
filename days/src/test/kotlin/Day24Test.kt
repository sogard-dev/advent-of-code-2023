package day24

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day24Test {

    private val testInput = """19, 13, 30 @ -2,  1, -2
18, 19, 22 @ -1, -1, -2
20, 25, 34 @ -2, -2, -4
12, 31, 28 @ -1, -2, -1
20, 19, 15 @  1, -5, -3""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("2", task1(testInput, 7.0, 27.0))
        assertEquals("16589", task1(readThisPuzzle(), 200000000000000.0, 400000000000000.0))
    }

    @Test
    fun testTaskTwo() {
        //374849304216208 too low
        assertEquals("FAIL", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day24")
    }
}