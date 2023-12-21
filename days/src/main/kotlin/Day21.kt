package day21

fun task1(input: List<String>, steps: Int): String {
    return calculateReachable(input, steps)
}

fun task2(input: List<String>): String {
    val base = 65
    val extra = 131
    for (steps in (0..5).map { base + it * extra }) {
        val task1 = calculateReachable(input, steps)
        println("$steps $task1")
    }
    return ""
}

private fun calculateReachable(input: List<String>, steps: Int): String {
    val size = input.size
    val mid = input.size / 2

    var points = setOf(mid + size * 99999 to mid + size * 99999)
    val seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var cnt = 0
    val ad = steps % 2

    for (i in 1..steps) {
        points = points.flatMap {
            listOf(
                it.first - 1 to it.second,
                it.first to it.second - 1,
                it.first + 1 to it.second,
                it.first to it.second + 1
            )

        }
            .distinct()
            .filter { input[it.first % size][it.second % size] != '#' && !seen.contains(it) }
            .toSet()

        if (i % 2 == ad) {
            cnt += points.size
        }

        seen += points
    }

    return cnt.toString()
}