package day2

fun task1(input: List<String>, blue: Int, red: Int, green: Int): Int {
    return input.map { parse(it) }.sumOf { g ->
        if (g.sets.any { c ->
                c.red > red || c.blue > blue || c.green > green
            }) {
            0
        } else {
            g.gameId
        }
    }
}

fun task2(input: List<String>): Int {
    return input.map { parse(it) }.sumOf { g ->
        g.sets.maxOf { c -> c.red } * g.sets.maxOf { c -> c.green } * g.sets.maxOf { c -> c.blue }
    }
}

private fun parse(s: String): Game {
    val spl = s.split(":")
    val gameId = Regex("Game (\\d+)").find(spl[0])!!.groups[1]!!.value.toInt()
    val setsSpl = spl[1].trim().split(";")

    val regex = Regex("((\\d+) ([a-z]+)[, ]*)")
    val cubes = setsSpl.map { l ->
        var green = 0
        var red = 0
        var blue = 0
        for (hit in regex.findAll(l)) {
            val toInt = hit.groups[2]!!.value.toInt()
            when (hit.groups[3]!!.value) {
                "green" -> green += toInt
                "red" -> red += toInt
                "blue" -> blue += toInt
            }
        }

        Cube(blue, red, green)
    }.toList()

    return Game(gameId, cubes)
}

private data class Game(val gameId: Int, val sets: List<Cube>)
private data class Cube(val blue: Int, val red: Int, val green: Int)