package day22

import util.getNumbers

fun task1(input: List<String>): String {
    val dropped = parseAndDrop(input)

    println("Calculating")
    return dropped.count {
        val above = findBlocksAbove(it, dropped)

        above.none { o ->
            findBlocksBelow(o, dropped).size == 1
        }
    }.toString()
}

fun task2(input: List<String>): String {
    val blocks = parseAndDrop(input)

    val above = blocks.associateWith {
        findBlocksAbove(it, blocks)
    }
    val below = blocks.associateWith {
        findBlocksBelow(it, blocks)
    }

    fun willDrop(init: Block): Int {
        val dropped = mutableListOf(init)

        var changed = true
        while (changed) {
            changed = false
            val above = dropped.flatMap { above[it]!! }.toSet()
            for (b in above) {
                if (!dropped.contains(b)) {
                    val blocksBelow = below[b]!!
                    if (!dropped.contains(b) && dropped.containsAll(blocksBelow)) {
                        dropped.add(b)
                        changed = true
                    }
                }
            }
        }
        dropped.remove(init)
        return dropped.size
    }

    println("Calculating")
    return blocks.sumOf {
        willDrop(it)
    }.toString()
}

private fun parseAndDrop(input: List<String>): MutableList<Block> {
    println("Parsing")

    val blocks = input.withIndex().map { (i, v) ->
        val l = getNumbers(v).map{it.toInt()}
        Block(i, l[0]..l[3], l[1]..l[4], l[2]..l[5])
    }

    val dropped = blocks.toMutableList()

    println("Dropping")
    var moved = 1
    while (moved > 0) {
        moved = 0
        val taken = dropped.flatMap { it.bricks }.toSet()

        for (idx in dropped.indices) {
            while (dropped[idx].z.first > 1 && dropped[idx].bricksBelow.none { taken.contains(it) }) {
                val block = dropped[idx]
                dropped[idx] = Block(block.id, block.x, block.y, (block.z.first - 1..<block.z.last))
                moved += 1
            }
        }
    }
    return dropped
}


private fun findBlocksAbove(block: Block, blocks: List<Block>): List<Block> {
    val bricks = block.bricks

    return blocks.filter {
        it != block && bricks.any { b -> it.bricksBelow.contains(b) }
    }
}

private fun findBlocksBelow(block: Block, blocks: List<Block>): List<Block> {
    val bricksBelow = block.bricksBelow

    return blocks.filter {
        it != block && bricksBelow.any { b -> it.bricks.contains(b) }
    }
}

private data class Brick(val x: Int, val y: Int, val z: Int)

private data class Block(val id: Int, val x: IntRange, val y: IntRange, val z: IntRange) {

    val bricks = bricksInternal().toSet()
    val bricksBelow = bricksBelowInternal().toSet()

    private fun bricksInternal(): List<Brick> {
        val list: MutableList<Brick> = mutableListOf()
        for (x in x) {
            for (y in y) {
                for (z in z) {
                    list.add(Brick(x, y, z))
                }
            }
        }
        return list
    }

    private fun bricksBelowInternal(): List<Brick> {
        val list: MutableList<Brick> = mutableListOf()
        for (x in x) {
            for (y in y) {
                list.add(Brick(x, y, z.first - 1))
            }
        }
        return list
    }
}