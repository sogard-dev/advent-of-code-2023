package day3

import kotlin.math.absoluteValue

fun task1(input: List<String>): String {
    val symbols: Map<Pair<Int, Int>, Char> = getSymbols(input)
    val numbers: List<Number> = getNumbers(input)

    return numbers
        .filter { n -> isNumberCloseToSymbols(n, symbols) }
        .sumOf { it.number.toInt() }
        .toString()
}


fun task2(input: List<String>): String {
    val symbols: Map<Pair<Int, Int>, Char> = getSymbols(input).filter { it.value == '*' }
    val numbers: List<Number> = getNumbers(input)

    return symbols
        .map { s ->
            Pair(s, numbers.filter { n ->
                isNumberCloseToSymbols(n, mapOf(s.key to s.value))
            })
        }
        .filter { it.second.size == 2 }
        .sumOf { it.second[0].number.toInt() * it.second[1].number.toInt() }
        .toString()
}

private fun getSymbols(input: List<String>): MutableMap<Pair<Int, Int>, Char> {
    val symbols: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
    val symbolRegex = Regex("([^.a-z\\d])")
    input.withIndex().forEach { (r, s) ->
        val matchResult = symbolRegex.findAll(s)
        matchResult.forEach {
            symbols[Pair(r, it.groups[1]!!.range.first)] = it.groups[1]!!.value[0]
        }
    }
    return symbols
}

private fun getNumbers(input: List<String>): MutableList<Number> {
    val numbers: MutableList<Number> = mutableListOf()
    val numberRegex = Regex("(\\d+)")
    input.withIndex().forEach { (r, s) ->
        val matchResult = numberRegex.findAll(s)
        matchResult.forEach {
            numbers.add(Number(r, it.groups[1]!!.range.first, it.groups[1]!!.value))
        }
    }
    return numbers
}

private fun isNumberCloseToSymbols(n: Number, symbols: Map<Pair<Int, Int>, Char>): Boolean {
    for (i in n.columnStart until (n.columnStart + n.number.length)) {
        if (symbols.any {
                val diff_r = it.key.first - n.row
                val diff_c = it.key.second - i

                diff_r.absoluteValue <= 1 && diff_c.absoluteValue <= 1
            }) {
            return true
        }
    }

    return false
}

private data class Number(val row: Int, val columnStart: Int, val number: String)