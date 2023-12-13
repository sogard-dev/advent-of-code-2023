package day13

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day13Test {
    private val testInput = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""

    @Test
    fun testTaskOne() {
        assertEquals("405", task1(testInput))
        assertEquals("43614", task1(readThisPuzzle().joinToString("\n")))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("400", task2(testInput))
        assertEquals("36771", task2(readThisPuzzle().joinToString("\n")))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day13")
    }
}