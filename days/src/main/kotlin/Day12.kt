package day12

import util.getNumbers

fun task1(input: List<String>): String {
    return input
            .map { solve(it) }
            .sum()
            .toString()
}

fun task2(input: List<String>): String {
    return input
            .map { expand(it) }
            .map { solve(it) }
            .sum()
            .toString()
}

fun expand(s: String): String {
    val input = s.split(" ")[0]
    val numbers = getNumbers(s.split(" ")[1]).joinToString(",")

    return "$input?$input?$input?$input?$input $numbers,$numbers,$numbers,$numbers,$numbers"
}

private fun solve(str: String): Long {
    val text = ".${str.split(" ")[0]}."
    val numbers = getNumbers(str.split(" ")[1])
    val cache: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()

    fun recurse(changeAt: Int, nextNumberIndex: Int): Long {
        if (nextNumberIndex == numbers.size) {
            if (text.substring(changeAt).contains('#')) {
                return 0
            }

            return 1
        }

        val cacheKey = Pair(changeAt, nextNumberIndex)
        val cacheHit = cache[cacheKey]
        if (cacheHit != null) {
            return cacheHit
        }

        val nextToFit = numbers[nextNumberIndex]
        var mustFit = false
        var optionLength = 0
        var options = 0L

        for (i in changeAt until text.length) {
            val c = text[i]
            if (!mustFit && c == '#') { //Must fit here
                mustFit = true
            }
            if (mustFit) {
                if (c == '.') {
                    options = 0
                    break
                } else {
                    optionLength++
                }

                if (optionLength == nextToFit) {
                    if (text[i + 1] == '#') {
                        options = 0
                        break
                    }

                    options += recurse(i + 2, nextNumberIndex + 1)
                    break
                }
            } else if (c == '?') {
                if (i + nextToFit >= text.length) {
                    options = 0
                    break
                }

                val canFitHere = text.subSequence(i until i + nextToFit).all { it == '#' || it == '?' }
                if (canFitHere && text[i + nextToFit] != '#') {
                    options += recurse(i + nextToFit + 1, nextNumberIndex + 1)
                }

                options += recurse(i + 1, nextNumberIndex)
                break
            }
        }

        cache[cacheKey] = options
        return options
    }

    return recurse(text.indexOfFirst { it == '?' || it == '#' }, 0)
}