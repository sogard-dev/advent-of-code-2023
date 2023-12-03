package day3

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test

internal class Day3Test {

    private val testInput = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598..""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("4361", task1(testInput))
        assertEquals("535235", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("467835", task2(testInput))
        assertEquals("79844424", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day3")
    }
}