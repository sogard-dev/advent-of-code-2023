package day17

import util.Direction
import util.GraphNode
import util.Visitor
import util.parseFromGrid

fun task1(input: List<String>): String {
    return solve(input, ::getNextNodesPart1)
}

fun task2(input: List<String>): String {
    return solve(input, ::getNextNodesPart2)
}

private fun solve(input: List<String>, nextNodes: (Visitor<Node>, List<Direction>) -> List<Pair<Node, List<Direction>>>): String {
    val grid = parseFromGrid(
        input,
        { r, c, v -> Node(r, c, v.toString().toInt()) },
        { _, _ -> true })

    val endNode = grid.bottomRightNode()!!
    val bootstrap = StateKey(grid.topLeftNode()!!, listOf()) to 0
    val closedSet: MutableMap<StateKey, Int> = mutableMapOf(bootstrap)
    val openSet = ArrayDeque(listOf(bootstrap))
    var shortestSeen = Int.MAX_VALUE
    while (openSet.isNotEmpty()) {
        val (key, loss) = openSet.removeFirst()
        val neighbours = nextNodes(grid.getVisitor(key.n), key.list)

        for ((n, l) in neighbours) {
            val newLoss = loss + n.heatloss
            val newStateKey = StateKey(n, l)
            val currentBest = closedSet[newStateKey] ?: Int.MAX_VALUE
            if (newLoss < currentBest) {
                if (n == endNode && shortestSeen > newLoss) {
                    shortestSeen = newLoss
                    println("Found $newLoss")
                }

                closedSet[newStateKey] = newLoss
                openSet.add(newStateKey to newLoss)
                openSet.sortBy { it.second }
            }
        }
    }

    val endOptions = closedSet.filter {
        it.key.n == endNode
    }

    return endOptions.minOf { it.value }.toString()
}

private fun getNextNodesPart1(v: Visitor<Node>, list: List<Direction>): List<Pair<Node, List<Direction>>> {
    val options = Direction.entries
        .filter {
            if (list.lastOrNull() == it.opposite()) {
                false
            } else if (list.size == 3) {
                it != list.first()
            } else {
                true
            }
        }

    val nextOptions = options.mapNotNull { d ->
        when (d) {
            Direction.RIGHT -> v.getRightNode()
            Direction.LEFT -> v.getLeftNode()
            Direction.UP -> v.getUpNode()
            Direction.DOWN -> v.getDownNode()
        }?.let {
            if (d == list.lastOrNull()) {
                it to listOf(*list.toTypedArray(), d).takeLast(3)
            } else {
                it to listOf(d)
            }
        }
    }
    return nextOptions
}

private fun getNextNodesPart2(v: Visitor<Node>, list: List<Direction>): List<Pair<Node, List<Direction>>> {
    val options = Direction.entries
        .filter {
            if (list.lastOrNull() == it.opposite()) {
                false
            } else if (list.isEmpty()) {
                true
            } else if (list.size < 4) {
                it == list.last()
            } else if (list.size >= 10) {
                it != list.last()
            } else {
                true
            }
        }

    val nextOptions = options.mapNotNull { d ->
        when (d) {
            Direction.RIGHT -> v.getRightNode()
            Direction.LEFT -> v.getLeftNode()
            Direction.UP -> v.getUpNode()
            Direction.DOWN -> v.getDownNode()
        }?.let {
            if (d == list.lastOrNull()) {
                it to listOf(*list.toTypedArray(), d).takeLast(10)
            } else {
                it to listOf(d)
            }
        }
    }
    return nextOptions
}

private data class StateKey(val n: Node, val list: List<Direction>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StateKey

        if (n != other.n) return false
        if (list != other.list) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + list.hashCode()
        return result
    }
}

private class Node(r: Int, c: Int, val heatloss: Int) : GraphNode(r, c) {
    override fun toString(): String {
        return "($r, $c) $heatloss"
    }
}
