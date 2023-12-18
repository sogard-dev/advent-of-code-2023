package day18

import kotlin.math.absoluteValue

fun task1(input: List<String>): String {
    return solve(input)
}

fun task2(input: List<String>): String {
    val regex = Regex("(\\w+)")
    return solve(input.map {
        val (_, _, hex) = regex.findAll(it).map { p -> p.groups[0]!!.value }.toList()
        val steps = hex.substring(0..4).toLong(16)
        val dir = when (hex[5]) {
            '0' -> "R"
            '1' -> "D"
            '2' -> "L"
            '3' -> "U"
            else -> throw RuntimeException()
        }

        "$dir $steps (#0dc571)"
    })
}

private fun solve(input: List<String>): String {
    val regex = Regex("(\\w+)")

    var lastPosition = 0L to 0L

    val positions = input.map {
        val (dir, bStr, _) = regex.findAll(it).map { p -> p.groups[0]!!.value }.toList()
        val len = bStr.toLong()

        val newPos = when (dir) {
            "R" -> lastPosition.first to lastPosition.second + len
            "L" -> lastPosition.first to lastPosition.second - len
            "U" -> lastPosition.first - len to lastPosition.second
            "D" -> lastPosition.first + len to lastPosition.second
            else -> throw RuntimeException()
        }

        val prev = lastPosition
        lastPosition = newPos
        prev to lastPosition
    }


    return calc(positions).toString()
}

private fun calc(edges: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>): Long {
    //https://en.wikipedia.org/wiki/Pick%27s_theorem
    //https://en.wikipedia.org/wiki/Shoelace_formula
    val area = edges.sumOf { (c, n) -> c.first * n.second - c.second * n.first }.absoluteValue / 2
    val boundary = edges.sumOf { e -> (e.second - e.first).manhatten() }

    return area + boundary / 2 + 1
}

private fun Pair<Long, Long>.manhatten(): Long {
    return first.absoluteValue + second.absoluteValue
}

private operator fun Pair<Long, Long>.minus(o: Pair<Long, Long>): Pair<Long, Long> {
    return o.first - this.first to o.second - this.second
}

