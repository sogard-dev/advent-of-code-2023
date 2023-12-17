package day17

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day17Test {
    private val testInput = """2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("102", task1(testInput))
        assertEquals("855", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("94", task2(testInput))
        assertEquals("980", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day17")
    }
}