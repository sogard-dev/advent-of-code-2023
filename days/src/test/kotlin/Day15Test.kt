package day15

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day15Test {
    
    @Test
    fun testTaskOne() {
        assertEquals("", task1(listOf()))
        assertEquals("", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("", task2(listOf()))
        assertEquals("", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day15")
    }
}