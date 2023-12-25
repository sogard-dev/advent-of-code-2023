package day25

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day25Test {
    
    @Test
    fun testTaskOne() {
        assertEquals("568214", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("", task2("""""".lines()))
        assertEquals("", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day25")
    }
}