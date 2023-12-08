package day8

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test

internal class Day8Test {

    private val testInput1 = """LLR
    
    AAA = (BBB, BBB)
    BBB = (AAA, ZZZ)
    ZZZ = (ZZZ, ZZZ)""".lines()

    private val testInput2 = """LR
    
    11A = (11B, XXX)
    11B = (XXX, 11Z)
    11Z = (11B, XXX)
    22A = (22B, XXX)
    22B = (22C, 22C)
    22C = (22Z, 22Z)
    22Z = (22B, 22B)
    XXX = (XXX, XXX)""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("6", task1(testInput1))
        assertEquals("17263", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("6", task2(testInput2))
        assertEquals("14631604759649", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day8")
    }
}