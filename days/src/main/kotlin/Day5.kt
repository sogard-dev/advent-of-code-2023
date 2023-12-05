package day5

import kotlin.math.max
import kotlin.math.min

fun task1(input: List<String>): String {
    val card = parse(input)
    return card.seeds.minOfOrNull { s -> getResultingNumber(card, s, 1) }.toString()
}

fun task2(input: List<String>): String {
    val card = parse(input)
    return card.seeds.windowed(2, 2).minOfOrNull {
        val (from, range) = it
        val resultingNumber = getResultingNumber(card, from, range)
        if (resultingNumber == 0L) {
            Long.MAX_VALUE
        } else {
            resultingNumber
        }
    }.toString()
}

private fun getResultingNumber(card: Card, seed: Long, range: Long): Long {
    var source = "seed"
    val srcRanges: MutableList<LongRange> = mutableListOf(seed until seed + range)
    val destRanges: MutableList<LongRange> = mutableListOf()

    while (source != "location") {
        val mapping = card.maps.find { it.source == source }!!

        while (srcRanges.isNotEmpty()) {
            destRanges.addAll(splitIntoRanges(srcRanges.removeFirst(), mapping.mapping))
        }

        source = mapping.destination
        srcRanges.addAll(destRanges)
        destRanges.clear()
    }

    return srcRanges.minOf { it.first }
}

fun splitIntoRanges(initialSrcRange: LongRange, mapping: List<Triple<Long, Long, Long>>): List<LongRange> {
    val destRanges: MutableList<LongRange> = mutableListOf()

    val srcRanges: MutableSet<LongRange> = mutableSetOf(initialSrcRange)
    while (srcRanges.isNotEmpty()) {
        val srcRange = srcRanges.random()
        srcRanges.remove(srcRange)

        var didWork = false
        mapping.forEach { (_, src, range) ->
            val (before, overlap, after) = overlap(srcRange, src until src + range)

            if (!before.isEmpty()) {
                srcRanges.add(before)
            }
            if (!after.isEmpty()) {
                srcRanges.add(after)
            }
            if (!overlap.isEmpty()) {
                if (srcRanges.add(overlap) && overlap != srcRange) {
                    didWork = true
                }
            }
        }

        if (!didWork) {
            destRanges.addAll(srcRanges.map { s ->
                val map = mapping.find { m -> s.first in m.second until m.second + m.third }
                if (map != null) {
                    val (dst, src, _) = map
                    val difference = s.first - src
                    val dstBase = dst + difference
                    (dstBase..dstBase + s.last - s.first)
                } else {
                    s
                }
            })
            srcRanges.clear()
        }
    }

    return destRanges.filter { !it.isEmpty() }
}

fun overlap(a: LongRange, b: LongRange): Triple<LongRange, LongRange, LongRange> {
    val overlaps = a.first <= b.last && a.last >= b.first
    if (overlaps) {
        val overlapRange = max(a.first, b.first)..min(a.last, b.last)
        val beforeRange = a.first until overlapRange.first
        val afterRange = overlapRange.last + 1..a.last

        return Triple(beforeRange, overlapRange, afterRange)
    } else {
        return Triple(a, LongRange.EMPTY, LongRange.EMPTY)
    }
}

private fun parse(input: List<String>): Card {
    val seeds = input[0].split(" ").mapNotNull { it.toLongOrNull() }.toList()

    val mapRegex = Regex("(\\w+)-to-(\\w+)")

    val maps = input.map { it.trim() }
            .joinToString("\n")
            .split("\n\n")
            .mapNotNull { b ->
                val lines = b.lines()
                val find = mapRegex.find(lines[0])
                if (find != null) {
                    val source = find.groups[1]!!.value
                    val destination = find.groups[2]!!.value
                    val mappings = (1 until lines.size)
                            .map { i -> lines[i].split(" ").mapNotNull { it.toLongOrNull() } }
                            .map { Triple(it[0], it[1], it[2]) }

                    Map(source, destination, mappings)
                } else {
                    null
                }
            }
    return Card(seeds, maps)
}


private data class Card(val seeds: List<Long>, val maps: List<Map>)
private data class Map(val source: String, val destination: String, val mapping: List<Triple<Long, Long, Long>>)