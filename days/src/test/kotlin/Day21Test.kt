package day21

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day21Test {

    private val testInput = """...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
...........""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("2", task1(testInput, 1))
        assertEquals("4", task1(testInput, 2))
        assertEquals("6", task1(testInput, 3))
        assertEquals("13", task1(testInput, 5))
        assertEquals("16", task1(testInput, 6))
        assertEquals("3858", task1(readThisPuzzle(), 64))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("16", task1(testInput, 6))
        assertEquals("50", task1(testInput, 10))
        assertEquals("1594", task1(testInput, 50))
        assertEquals("6536", task1(testInput, 100))

        assertEquals("", task2(readThisPuzzle()))
        val steps = 26501365
        val addDelta = 31098
        val stepDelta = 131

        var step = 327L
        var sum = 97407L
        var add = 62281L
        while (step < steps) {
            step += stepDelta
            add += addDelta
            sum += add
        }

        println("$step $sum")
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day21")
    }
}