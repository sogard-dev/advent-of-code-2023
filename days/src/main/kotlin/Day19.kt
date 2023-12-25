package day19

import util.getNumbers

fun task1(input: List<String>): String {
    val splitter = input.indexOf("")
    val (workflows, itemsStr) = input.subList(0, splitter) to input.subList(splitter + 1, input.size)

    val wfs = parseToWorkflow(workflows)
    val items = itemsStr.map {
        val (x, m, a, s) = getNumbers(it).map{it.toInt()}
        Item(x, m, a, s)
    }

    items.forEach { wfs.evaluate(it) }

    return wfs.accepted.sumOf { it.x + it.m + it.a + it.s }.toString()
}

fun task2(input: List<String>): String {
    val stateToRules = getStateToRules(input.takeWhile { it != "" })

    val rangesAtAccepted: MutableList<List<IntRange>> = mutableListOf()

    fun recurse(state: String, ranges: List<IntRange>) {
        if (state == "R" || ranges.any { it.isEmpty() }) {
            return
        }
        if (state == "A") {
            rangesAtAccepted.add(ranges)
            return
        }

        val remaining = ranges.toMutableList()
        for (rule in stateToRules[state]!!) {
            val (x, m, a, s) = remaining

            val nextList = when (rule.compareChar) {
                '<' -> {
                    when (rule.attr) {
                        'x' -> listOf(x.first until rule.value, m, a, s)
                        'm' -> listOf(x, m.first until rule.value, a, s)
                        'a' -> listOf(x, m, a.first until rule.value, s)
                        's' -> listOf(x, m, a, s.first until rule.value)
                        else -> throw RuntimeException()
                    }
                }

                '>' -> {
                    when (rule.attr) {
                        'x' -> listOf(rule.value + 1..x.last, m, a, s)
                        'm' -> listOf(x, rule.value + 1..m.last, a, s)
                        'a' -> listOf(x, m, rule.value + 1..a.last, s)
                        's' -> listOf(x, m, a, rule.value + 1..s.last)
                        else -> throw RuntimeException()
                    }
                }

                '_' -> remaining
                else -> throw RuntimeException()
            }

            val idx = attrToIdx(rule.attr)
            if (idx >= 0) {
                val removed = remaining[idx].minus(nextList[idx].toSet())
                remaining[idx] = (removed.first()..removed.last())
            }
            recurse(rule.target, nextList)
        }
    }

    recurse("in", listOf(1..4000, 1..4000, 1..4000, 1..4000))

    return rangesAtAccepted.sumOf { l ->
        l.fold(1L) { acc, r -> acc * r.count().toLong() }
    }.toString()
}

private fun attrToIdx(c: Char): Int {
    return when (c) {
        'x' -> 0
        'm' -> 1
        'a' -> 2
        's' -> 3
        else -> -1
    }
}

private fun parseToWorkflow(str: List<String>): WorkFlow {
    val evaluated = getStateToRules(str).map { (key, value) ->
        key to { i: Item ->
            value.first { r -> r.evaluate(i) }.target
        }
    }.toMap()

    return WorkFlow(evaluated, mutableListOf(), mutableListOf())
}

private fun getStateToRules(str: List<String>): Map<String, List<Rule>> {
    val map = str.associate {
        val name = it.substringBefore('{')
        val rulesStr = it.substringAfter('{').takeWhile { c -> c != '}' }

        val rules = rulesStr.split(",").map { s ->
            if (!s.contains(":")) {
                Rule('_', 0, '_', s)
            } else {
                val (attr, valueStr) = s.split('<', '>')
                val (value, target) = valueStr.split(":")
                Rule(attr[0], value.toInt(), s[1], target)
            }
        }

        name to rules
    }
    return map
}

private data class Rule(val attr: Char, val value: Int, val compareChar: Char, val target: String) {
    fun evaluate(item: Item): Boolean {
        val aVal = when (attr) {
            'x' -> item.x
            'm' -> item.m
            'a' -> item.a
            's' -> item.s
            '_' -> 0
            else -> throw RuntimeException()
        }

        return when (compareChar) {
            '<' -> aVal < value
            '>' -> aVal > value
            else -> true
        }
    }
}

private data class Item(val x: Int, val m: Int, val a: Int, val s: Int)

private data class WorkFlow(val flows: Map<String, (Item) -> String>, val accepted: MutableList<Item>, val rejected: MutableList<Item>) {
    fun evaluate(item: Item) {
        var state = "in"
        while (state != "R" && state != "A") {
            state = flows[state]!!(item)
            when (state) {
                "R" -> rejected.add(item)
                "A" -> accepted.add(item)
            }
        }
    }
}