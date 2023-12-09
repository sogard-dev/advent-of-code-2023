package day9

import util.getNumbers

fun task1(input: List<String>): String {
    return input.sumOf { s ->
        solve(getNumbers(s),
            { it.last() },
            { l -> l.sum().toLong() })
    }.toString()
}

fun task2(input: List<String>): String {
    return input.sumOf { s ->
        solve(getNumbers(s),
            { it.first() },
            { l ->
                var num = 0L
                l.reversed().forEach {
                    num = it - num
                }
                num
            })
    }.toString()
}

fun solve(numbers: List<Int>, fetcher: (List<Int>) -> Int, summer: (List<Int>) -> Long): Long {
    val collectedNumbers: MutableList<Int> = mutableListOf()

    var currentNumbers = numbers
    while (currentNumbers.any { it != 0 }) {
        collectedNumbers += fetcher(currentNumbers)

        currentNumbers = currentNumbers.windowed(2, 1) {
            it[1] - it[0]
        }
    }

    return summer(collectedNumbers)
}