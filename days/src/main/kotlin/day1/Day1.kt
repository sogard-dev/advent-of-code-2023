package day1

fun task1(input: List<String>): String {
    val mapping = mapOf(
        "1" to "1",
        "2" to "2",
        "3" to "3",
        "4" to "4",
        "5" to "5",
        "6" to "6",
        "7" to "7",
        "8" to "8",
        "9" to "9",
    )

    return calibration(input, mapping)
}

fun task2(input: List<String>): String {
    val mapping = mapOf(
        "1" to "1",
        "2" to "2",
        "3" to "3",
        "4" to "4",
        "5" to "5",
        "6" to "6",
        "7" to "7",
        "8" to "8",
        "9" to "9",
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    return calibration(input, mapping)
}

private fun calibration(input: List<String>, mapping: Map<String, String>): String {
    return input.map { s ->
        val firstHits = mapping.map { m ->
            s.indexOf(m.key) to m.value
        }.filter { it.first >= 0 }.toList()
        val first = firstHits.minByOrNull { it.first }!!.second

        val lastHits = mapping.map { m ->
            s.lastIndexOf(m.key) to m.value
        }.filter { it.first >= 0 }.toList()
        val last = lastHits.maxByOrNull { it.first }!!.second

        "$first$last".toInt()
    }.sum().toString()
}