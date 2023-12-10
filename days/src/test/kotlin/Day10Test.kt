package day10

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day10Test {

    private val testInput = """..F7.
.FJ|.
SJ.L7
|F--J
LJ...""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("8", task1(testInput))
        assertEquals("6733", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        val expandedString = task2Expand(readThisPuzzle())
        val answer = task2Color(expandedString.lines())
        assertEquals("435", answer)
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day10")
    }
}