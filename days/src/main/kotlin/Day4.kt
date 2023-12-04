package day4

fun task1(input: List<String>): String {
    return input.sumOf { l ->
        parse(l).multWinningNumbers()
    }.toString()
}

fun task2(input: List<String>): String {
    val cards = input.map { parse(it) }
    val amt = IntArray(cards.size) { 1 }

    cards.withIndex().forEach {
        val pointsForCard = it.value.winningNumbers()
        val range = it.index + 1..it.index + pointsForCard
        for (i in range) {
            amt[i] += amt[it.index]
        }
    }

    return amt.sum().toString()
}


private fun parse(l: String): Card {
    val spl = l.split(":")
    val (winStr, numStr) = spl[1].split("|")
    val win = winStr.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    val num = numStr.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    return Card(win, num)
}

private data class Card(val win: List<Int>, val num: List<Int>) {
    fun multWinningNumbers(): Int {
        var sum = 0
        for (n in num.filter { win.contains(it) }) {
            if (sum == 0) {
                sum = 1
            } else {
                sum *= 2
            }
        }
        return sum
    }

    fun winningNumbers(): Int {
        return num.count { win.contains(it) }
    }
}