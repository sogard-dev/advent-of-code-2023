package day13

fun task1(input: String): String {
    return input.split("\n\n")
            .sumOf { solve(it) }
            .toString()
}

fun task2(input: String): String {
    return input.split("\n\n")
            .sumOf { solve2(it) }
            .toString()
}

fun solve2(input: String): Int {
    val org = solve(input)

    fun flip(c: Char): String {
        return when (c) {
            '#' -> "."
            '.' -> "#"
            else -> c.toString()
        }
    }

    for (i in input.indices) {
        val changedInput = input.replaceRange(i..i, flip(input[i]))
        val newVal = solve(changedInput, org)
        if (newVal != 0 && newVal != org) {
            return newVal
        }
    }

    TODO("this cant be good")
}

fun solve(input: String, ignoredValue: Int = 0): Int {
    val blocks = input.lines()

    val rows = (0 until blocks.size - 1).filter { row ->
        var isGood = true
        for (diff in 0..blocks.size) {
            val u = row - diff
            val d = row + diff + 1
            if (u >= 0 && d in blocks.indices && isGood) {
                isGood = blocks[u] == blocks[d]
            }
        }

        isGood
    }

    val columns = (0 until blocks[0].length - 1).filter { c ->
        var isGood = true
        for (diff in 0..blocks[0].length) {
            val l = c - diff
            val r = c + diff + 1
            if (l >= 0 && r in blocks[0].indices && isGood) {
                isGood = blocks.indices.all { blocks[it][l] == blocks[it][r] }
            }
        }

        isGood
    }

    val rowSum = rows.map { (it + 1) * 100 }.filter { it != ignoredValue }.sum()
    val columnSum = columns.map { it + 1 }.filter { it != ignoredValue }.sum()

    return rowSum + columnSum
}