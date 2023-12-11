package day11

fun task1(input: List<String>): String {
    return (solve(input, 2).sum() / 2).toString()
}

fun task2(input: List<String>, expansion: Int): String {
    return (solve(input, expansion).sum() / 2).toString()
}

fun solve(input: List<String>, expansion: Int): List<Long> {
    val nodes = getNodes(input)
    val (expandRow, expandColumn) = getExpansion(input)

    return nodes.flatMap { n1 ->
        nodes.map { n2 -> n1 to n2 }
                .filter { it.first != it.second }
                .map { expandedManhattenDistance(it.first, it.second, expandRow, expandColumn, expansion) }
    }
}

fun expandedManhattenDistance(n1: Pair<Int, Int>, n2: Pair<Int, Int>, expandRow: List<Int>, expandColumn: List<Int>, expansion: Int): Long {
    val rows = expandRow.toMutableList()
    val columns = expandColumn.toMutableList()

    var steps = 0L
    for (r in minOf(n1.first, n2.first) until maxOf(n1.first, n2.first)) {
        if (rows.remove(r)) {
            steps += expansion
        } else {
            steps += 1
        }
    }

    for (c in minOf(n1.second, n2.second) until maxOf(n1.second, n2.second)) {
        if (columns.remove(c)) {
            steps += expansion
        } else {
            steps += 1
        }
    }

    return steps
}

private fun getNodes(input: List<String>): List<Pair<Int, Int>> {
    val galRex = Regex("#")
    val nodes = input.withIndex().flatMap { (r, v) ->
        val findAll = galRex.findAll(v)

        findAll.map { m ->
            Pair(r, m.groups[0]!!.range.first)
        }
    }
    return nodes
}

private fun getExpansion(input: List<String>): Pair<List<Int>, List<Int>> {
    val rows: List<Int> = input.withIndex().filter { i -> i.value.all { it == '.' } }.map { it.index }
    val columns = input[0].withIndex().filter { i -> input.indices.all { input[it][i.index] == '.' } }.map { it.index }
    return Pair(rows, columns)
}
