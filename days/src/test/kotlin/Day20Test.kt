package day20

import org.junit.jupiter.api.Assertions.assertEquals
import util.lcm
import util.readPuzzle
import kotlin.test.Test


internal class Day20Test {

    @Test
    fun testTaskOne() {
        assertEquals("32000000", task1("""broadcaster -> a, b, c
%a -> b
%b -> c
%c -> inv
&inv -> a""".lines(), 1000))

        assertEquals("680278040", task1(readThisPuzzle(), 1000))
    }

    @Test
    fun testTaskTwo() {
//        assertEquals("FAIL", task2(readThisPuzzle()))
        assertEquals("243548140870057", lcm(3881L, 3889L, 4013L, 4021L))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day20")
    }
}