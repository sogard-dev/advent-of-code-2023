package day16

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day16Test {

    private val testInput = """.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|....""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("46", task1(testInput))
        assertEquals("7979", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("51", task2(testInput))
        assertEquals("8437", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day16")
    }
}