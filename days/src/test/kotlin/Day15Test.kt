package day15

import org.junit.jupiter.api.Assertions.assertEquals
import util.readPuzzle
import kotlin.test.Test


internal class Day15Test {
    
    @Test
    fun testTaskOne() {
        assertEquals("1320", task1("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(",")))
        assertEquals("513172", task1(readThisPuzzle()))
    }

    @Test
    fun testTaskTwo() {
        assertEquals("145", task2("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(",")))
        assertEquals("237806", task2(readThisPuzzle()))
    }

    private fun readThisPuzzle(): List<String> {
        return readPuzzle("day15")[0].split(",")
    }
}