package day7

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test

internal class Day7Test {

    val testInput = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("6440", task1(testInput))
        assertEquals("245794640", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("5905", task2(testInput))
        assertEquals("247899149", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day7")
    }
}