package day14

import util.Graph
import util.GraphNode
import util.Visitor
import util.parseFromGrid

fun task1(input: List<String>): String {
    val grid = parse(input)
    moveAll(grid) { it.getUpNode() }
    return calculateLoad(grid)
}

fun task2(input: List<String>, spins: Long): String {
    val grid = parse(input)

    val cache: HashMap<List<Pair<Int, Int>>, Long> = HashMap()
    var n = 0L
    while (n < spins) {
        moveAll(grid) { it.getUpNode() }
        moveAll(grid) { it.getLeftNode() }
        moveAll(grid) { it.getDownNode() }
        moveAll(grid) { it.getRightNode() }

        val sortedPositions = getSortedPositions(grid)
        val seenBefore = cache[sortedPositions]
        if (seenBefore != null) {
            val diff = n - seenBefore
            while (n + diff < spins) {
                n += diff
            }
        } else {
            cache[sortedPositions] = n
        }
        n++
    }

    return calculateLoad(grid)
}

private fun parse(input: List<String>) = parseFromGrid(input, { r, c, v -> Node(r, c, Type.fromString(v)) }, { _, _ -> true })

private fun getSortedPositions(grid: Graph<Node>): List<Pair<Int, Int>> {
    return grid.nodes()
            .filter { it.t == Type.Movable }
            .map { Pair(it.r, it.c) }
            .sortedWith(compareBy({ it.first }, { it.second }))
}

private fun calculateLoad(grid: Graph<Node>): String {
    val height = grid.getHeight()
    return grid.nodes()
            .filter { it.t == Type.Movable }
            .sumOf { height - it.r + 1 }
            .toString()
}

private fun moveAll(grid: Graph<Node>, way: (Visitor<Node>) -> Node?) {
    var movedSomething = true
    while (movedSomething) {
        movedSomething = false

        grid.nodesWithVisitor().forEach { (n, v) ->
            if (n.t == Type.Movable) {
                val nodeInWay = way(v)
                nodeInWay?.let { up ->
                    if (up.t == Type.Empty) {
                        n.t = Type.Empty
                        up.t = Type.Movable
                        movedSomething = true
                    }
                }
            }
        }
    }
}

private enum class Type(val c: Char) {
    Empty('.'), Movable('O'), Blocking('#');

    companion object {
        fun fromString(s: Char) = entries.find { it.c == s }!!
    }
}

private class Node(r: Int, c: Int, var t: Type) : GraphNode(r, c) {
    override fun toString() = t.c.toString()
}