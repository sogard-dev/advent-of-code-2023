package day6

fun task1(input: List<String>): String {
    val times = parseNumbers(input[0])
    val distances = parseNumbers(input[1])
    return times.indices.map { howManyCanBeat(times[it], distances[it]) }.fold(1) { acc, v -> acc * v }.toString()
}

fun task2(input: List<String>): String {
    val times = parseNumbers(input[0].replace(" ", ""))
    val distances = parseNumbers(input[1].replace(" ", ""))
    return times.indices.sumOf { howManyCanBeat(times[it], distances[it]) }.toString()
}

private fun howManyCanBeat(allowedTime: Long, distance: Long): Int {
    return (0..allowedTime).count { isFaster(it, allowedTime, distance) }
}

fun isFaster(pressTime: Long, allowedTime: Long, recordDistance: Long): Boolean {
    return pressTime * (allowedTime - pressTime) > recordDistance
}

fun parseNumbers(s: String): List<Long> {
    val regex = Regex("(\\d+)")
    return regex.findAll(s).map { g -> g.groups[1]!!.value.toLong() }.toList()
}