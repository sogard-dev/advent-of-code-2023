package day22

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day22Test {

    private val testInput = """1,0,1~1,2,1
0,0,2~2,0,2
0,2,3~2,2,3
0,0,4~0,2,4
2,0,5~2,2,5
0,1,6~2,1,6
1,1,8~1,1,9"""

    @Test
    fun testTaskOne() {
        assertEquals("5", task1(testInput.lines()))
        assertEquals("468", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("7", task2(testInput.lines()))
        assertEquals("75358", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day22")
    }
}