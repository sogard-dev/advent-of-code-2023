package day23

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day23Test {
    
    @Test
    fun testTaskOne() {
        assertEquals("", task1("""""".lines()))
        assertEquals("", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("", task2("""""".lines()))
        assertEquals("", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day23")
    }
}