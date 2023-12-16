package day16

import kotlin.math.max

fun task1(input: List<String>): String {
    val light = setOf(Triple(0, -1, Direction.RIGHT))
    return solve(light, input).toString()
}

fun task2(input: List<String>): String {
    val maxFromRows = (input.indices)
        .maxOf {
            max(
                solve(setOf(Triple(it, -1, Direction.RIGHT)), input),
                solve(setOf(Triple(it, input[it].length, Direction.LEFT)), input)
            )
        }

    val maxFromColumns = (input[0].indices)
        .maxOf {
            max(
                solve(setOf(Triple(-1, it, Direction.DOWN)), input),
                solve(setOf(Triple(input.size, it, Direction.UP)), input)
            )
        }

    return max(maxFromRows, maxFromColumns).toString()
}


private fun solve(light: Set<Triple<Int, Int, Direction>>, input: List<String>): Int {
    var beams = light
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val configurationSeenBefore: MutableSet<Triple<Int, Int, Direction>> = mutableSetOf()
    while (beams.isNotEmpty()) {
        beams = beams.flatMap {
            //Move then update
            val (nr, nc) = Pair(it.first + it.third.dr, it.second + it.third.dc)
            if (nr !in input.indices || nc !in input[0].indices || !configurationSeenBefore.add(it)) {
                listOf()
            } else {
                visited.add(nr to nc)

                when (val c = input[nr][nc]) {
                    '.' -> listOf(Triple(nr, nc, it.third))
                    else -> when (c) {
                        '-' -> when (it.third) {
                            Direction.RIGHT -> listOf(Triple(nr, nc, Direction.RIGHT))
                            Direction.LEFT -> listOf(Triple(nr, nc, Direction.LEFT))
                            Direction.UP -> listOf(Triple(nr, nc, Direction.LEFT), Triple(nr, nc, Direction.RIGHT))
                            Direction.DOWN -> listOf(Triple(nr, nc, Direction.LEFT), Triple(nr, nc, Direction.RIGHT))
                        }

                        '|' -> when (it.third) {
                            Direction.UP -> listOf(Triple(nr, nc, Direction.UP))
                            Direction.DOWN -> listOf(Triple(nr, nc, Direction.DOWN))
                            Direction.LEFT -> listOf(Triple(nr, nc, Direction.UP), Triple(nr, nc, Direction.DOWN))
                            Direction.RIGHT -> listOf(Triple(nr, nc, Direction.UP), Triple(nr, nc, Direction.DOWN))
                        }

                        '/' -> when (it.third) {
                            Direction.RIGHT -> listOf(Triple(nr, nc, Direction.UP))
                            Direction.DOWN -> listOf(Triple(nr, nc, Direction.LEFT))
                            Direction.LEFT -> listOf(Triple(nr, nc, Direction.DOWN))
                            Direction.UP -> listOf(Triple(nr, nc, Direction.RIGHT))
                        }

                        '\\' -> when (it.third) {
                            Direction.RIGHT -> listOf(Triple(nr, nc, Direction.DOWN))
                            Direction.DOWN -> listOf(Triple(nr, nc, Direction.RIGHT))
                            Direction.LEFT -> listOf(Triple(nr, nc, Direction.UP))
                            Direction.UP -> listOf(Triple(nr, nc, Direction.LEFT))
                        }

                        else -> throw RuntimeException("Unknown: ($nr, $nc) '$c'")
                    }
                }
            }
        }.toSet()
    }
    return visited.size
}

private enum class Direction(val dr: Int, val dc: Int) {
    UP(-1, 0), DOWN(1, 0), RIGHT(0, 1), LEFT(0, -1)
}