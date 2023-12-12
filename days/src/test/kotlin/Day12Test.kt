package day12

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day12Test {

    private val testInput = """???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1""".lines()

    @Test
    fun tests() {
        assertEquals("4", task1("????.######..#####. 1,6,5".lines()))
        assertEquals("3", task1(".#???#.#??.#???. 5,1,1,1".lines()))
        assertEquals("1", task1("???.### 1,1,3".lines()))
        assertEquals("4", task1(".??..??...?##. 1,1,3".lines()))
        assertEquals("1", task1("?#?#?#?#?#?#?#? 1,3,1,6".lines()))
        assertEquals("1", task1("????.#...#... 4,1,1".lines()))
        assertEquals("10", task1("?###???????? 3,2,1".lines()))

        assertEquals("21", task1(testInput))
        assertEquals("525152", task2(testInput))
    }

    @Test
    fun taskOne() {
        assertEquals("7251", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("2128386729962", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day12")
    }
}