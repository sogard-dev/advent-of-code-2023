package day7

fun task1(input: List<String>): String {
    val cards = input.map { parsePart1(it) }
    val ranked = cards.sorted()
    return ranked
            .withIndex()
            .sumOf { (i, v) -> (i + 1) * v.bid }
            .toString()
}

fun task2(input: List<String>): String {
    val cards = input.map { parsePart2(it) }
    val ranked = cards.sorted()
    return ranked
            .withIndex()
            .sumOf { (i, v) -> (i + 1) * v.bid }
            .toString()
}

private fun parsePart1(text: String): Card {
    val (cardsStr, bidStr) = text.split(" ")
    return Card(text, bidStr.toInt(), getType(cardsStr), 11)
}

private fun parsePart2(text: String): Card {
    val (cardsStr, bidStr) = text.split(" ")

    val chars = cardsStr.toSortedSet()
    val hasJ = chars.remove('J')
    return if (hasJ && chars.isNotEmpty()) {
        val cardList = chars.map {
            Card(cardsStr, bidStr.toInt(), getType(cardsStr.replace('J', it)), 0)
        }
        cardList.maxOf { it }
    } else {
        Card(cardsStr, bidStr.toInt(), getType(cardsStr), 0)
    }
}

private fun getType(cardsStr: String): Type {
    val charArr = cardsStr.toCharArray()
    charArr.sort();
    return getTypeFromSortedString(charArr.joinToString(""))
}

private fun getTypeFromSortedString(str: String): Type {
    if (Regex("(\\w)\\1{4}").containsMatchIn(str)) {
        return Type.FIVE_OF_A_KIND
    }

    if (Regex("(\\w)\\1{3}").containsMatchIn(str)) {
        return Type.FOUR_OF_A_KIND
    }

    if (Regex("(\\w)\\1{2}").containsMatchIn(str)) {
        if (Regex("([^${str[2]}])\\1").containsMatchIn(str)) {
            return Type.FULL_HOUSE
        }
        return Type.THREE_OF_A_KIND
    }

    val pairCount = Regex("(\\w)\\1").findAll(str).count()
    if (pairCount == 2) {
        return Type.TWO_PAIR
    }
    if (pairCount == 1) {
        return Type.ONE_PAIR
    }

    return Type.HIGH_CARD;
}

private data class Card(val text: String, val bid: Int, val type: Type, val jValue: Int) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        if (type.ordinal != other.type.ordinal) {
            return type.ordinal - other.type.ordinal
        }

        for (i in text.indices) {
            if (text[i] != other.text[i]) {
                return cardToInt(this, i) - cardToInt(other, i)
            }
        }

        return 0
    }

    private fun cardToInt(other: Card, i: Int) = when (val c = other.text[i]) {
        'T' -> 10
        'J' -> jValue
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> c.toString().toInt()
    }
}

private enum class Type { HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND }