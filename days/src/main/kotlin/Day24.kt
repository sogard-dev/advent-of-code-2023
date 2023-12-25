package day24

import util.getNumbers
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun task1(input: List<String>, from: Double, to: Double): String {
    val hails = input.map {
        val numbers = getNumbers(it).map { i -> i.toDouble() }
        Hail(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5])
    }

    var cnt = 0
    for (i in hails.indices) {
        for (j in i + 1..hails.indices.last) {
            if (intersects(hails[i], hails[j], from, to)) {
                cnt++
            }
        }
    }
    return cnt.toString()
}

private fun intersects(h1: Hail, h2: Hail, from: Double, to: Double): Boolean {
    println("Hailstone A: $h1")
    println("Hailstone B: $h2")

    val t1 = intersectT1(h1, h2)
    if (t1.isInfinite()) {
        println("Hailstones' paths are parallel; they never intersect.")
    } else {
        val t2 = intersectT2(h1, h2, t1)
        val x = h1.px + h1.vx * t1
        val y = h1.py + h1.vy * t1

        if (t1 < 0 || t2 < 0) {
            println("Hailstones' paths crossed in the past.")
        } else if (x in from..to && y in from..to) {
            println("Hailstones' paths will cross inside the test area (at x=$x, y=$y).")
            println()
            return true
        } else {
            println("Hailstones' paths will cross outside the test area (at x=$x, y=$y).")
        }
    }

    println()
    return false
}

private fun intersectT1(h1: Hail, h2: Hail): Double {
    return ((h1.py - h2.py) * h2.vx - (h1.px - h2.px) * h2.vy) / (h2.vy * h1.vx - h1.vy * h2.vx)
}

private fun intersectT2(h1: Hail, h2: Hail, t1: Double): Double {
    return (h1.px + h1.vx * t1 - h2.px) / h2.vx
}


fun task2(input: List<String>): String {
    val hails = input.map {
        val numbers = getNumbers(it).map { i -> i.toDouble() }
        Hail(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5])
    }

    val vx = (-1000..1000).toMutableSet()
    val vy = (-1000..1000).toMutableSet()
    val vz = (-1000..1000).toMutableSet()

    hails.forEach { h1 ->
        hails.forEach { h2 ->
            if (h1 != h2) {
                if (h1.vx == h2.vx) {
                    val distance = (h1.px - h2.px).absoluteValue
                    for (v in vx.toList()) {
                        if (distance % (v - h1.vx) != 0.0) {
                            vx.remove(v)
                        }
                    }
                }
                if (h1.vy == h2.vy) {
                    val distance = (h1.py - h2.py).absoluteValue
                    for (v in vy.toList()) {
                        if (distance % (v - h1.vy) != 0.0) {
                            vy.remove(v)
                        }
                    }
                }

                if (h1.vz == h2.vz) {
                    val distance = (h1.pz - h2.pz).absoluteValue
                    for (v in vz.toList()) {
                        if (distance % (v - h1.vz) != 0.0) {
                            vz.remove(v)
                        }
                    }
                }
            }
        }
    }

    println("V: $vx, $vy, $vz")
    val (rvx, rvy, rvz) = listOf(vx.first(), vy.first(), vz.first())
    val (a, b) = hails.take(2)
    val ma = (a.vy - rvy) / (a.vx - rvx)
    val mb = (b.vy - rvy) / (b.vx - rvx)
    val ca = a.py - (ma * a.px)
    val cb = b.py - (mb * b.px)
    val rpx = ((cb - ca) / (ma - mb)).roundToLong()
    val rpy = (ma * rpx + ca).roundToLong()
    val time = ((rpx - a.px) / (a.vx - rvx)).roundToLong()
    val rpz = (a.pz + (a.vz - rvz) * time).roundToLong()

    println("$rpx, $rpy, $rpz")

    return (rpx + rpy + rpz).toString()
}


private data class Hail(val px: Double, val py: Double, val pz: Double, val vx: Double, val vy: Double, val vz: Double)