package day11

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day11Test {
    private val testInput = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("374", task1(testInput))
        assertEquals("10231178", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("1030", task2(testInput, 10))
        assertEquals("8410", task2(testInput, 100))
        assertEquals("622120986954", task2(readThisPuzzle(), 1000000))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day11")
    }
}