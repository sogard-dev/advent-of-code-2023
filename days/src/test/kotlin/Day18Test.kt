package day18

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day18Test {

    private val testInput = """R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("62", task1(testInput))
        assertEquals("48652", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("952408144115", task2(testInput))
        assertEquals("45757884535661", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day18")
    }
}