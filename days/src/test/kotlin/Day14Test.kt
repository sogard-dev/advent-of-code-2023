package day14

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day14Test {
    private val testInput = """O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("136", task1(testInput))
        assertEquals("", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("64", task2(testInput, 1000000000))
        assertEquals("93102", task2(readThisPuzzle(), 1000000000))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day14")
    }
}