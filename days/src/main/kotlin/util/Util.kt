package util

import java.util.*

fun gcd(vararg numbers: Long): Long {
    return Arrays.stream(numbers).reduce(0) { x, y -> gcd(x, y) }
}

fun lcm(vararg numbers: Long): Long {
    return Arrays.stream(numbers).reduce(1) { x, y -> x * (y / gcd(x, y)) }
}

fun getNumbers(s: String) =
    Regex("(-?\\d+)").findAll(s).map {
        it.groups[1]!!.value.toInt()
    }.toList()

private fun gcd(x: Long, y: Long): Long {
    return if (y == 0L) x else gcd(y, x % y)
}