package day23

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day23Test {

    private val testInput = """#.#####################
#.......#########...###
#######.#########.#.###
###.....#.>.>.###.#.###
###v#####.#v#.###.#.###
###.>...#.#.#.....#...#
###v###.#.#.#########.#
###...#.#.#.......#...#
#####.#.#.#######.#.###
#.....#.#.#.......#...#
#.#####.#.#.#########v#
#.#...#...#...###...>.#
#.#.#v#######v###.###v#
#...#.>.#...>.>.#.###.#
#####v#.#.###v#.#.###.#
#.....#...#...#.#.#...#
#.#########.###.#.#.###
#...###...#...#...#.###
###.###.#.###v#####v###
#...#...#.#.>.>.#.>.###
#.###.###.#.###.#.#v###
#.....###...###...#...#
#####################.#""".lines()

    @Test
    fun testTaskOne() {
        assertEquals("94", task1(testInput))
        assertEquals("2162", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("154", task2(testInput))
        assertEquals("6334", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day23")
    }
}