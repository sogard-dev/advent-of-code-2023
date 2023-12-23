package day23

import util.Graph
import util.GraphNode
import util.parseFromGrid

fun task1(input: List<String>): String {
    val grid = parseFromGrid(input,
        { r, c, v -> Node(r, c, v) },
        { n1, n2 ->
            if (n1.v == '#' || n2.v == '#') {
                false
            } else {
                when (n1.v) {
                    '.' -> true
                    '>' -> n1.isLeftOf(n2)
                    '<' -> n1.isRightOf(n2)
                    'v' -> n1.isAbove(n2)
                    else -> false
                }
            }
        })

    return longestPath(grid)
}

fun task2(input: List<String>): String {
    val grid = parseFromGrid(input,
        { r, c, v -> Node(r, c, v) },
        { n1, n2 ->
            !(n1.v == '#' || n2.v == '#')
        })

    return longestPath(grid)
}

private fun longestPath(grid: Graph<Node>): String {
    val h = grid.getHeight()
    val w = grid.getWidth()
    val startNode = grid.find { it.c == 1 && it.r == 0 }!!
    val endNode = grid.find { it.c == w - 1 && it.r == h }!!

    val junctions = grid.nodes()
        .filter { n ->
            val size = grid.connections(n).size
            size != 2 && size > 0
        }

    val distances: Map<Node, MutableList<Pair<Node, Int>>> = junctions.associateWith {
        mutableListOf()
    }

    junctions.forEach { j ->
        grid.bfsUntil(j) { n, d ->
            val isJunction = junctions.contains(n)
            if (isJunction) {
                distances[j]!! += n to d
            }
            !isJunction
        }
    }

    fun recurse(n: Node, visited: Set<Node>, d: Int): Int {
        return if (n == endNode) {
            d
        } else {
            distances[n]!!.maxOf { (dn, dd) ->
                if (!visited.contains(dn)) {
                    recurse(dn, visited + dn, d + dd)
                } else {
                    0
                }
            }
        }
    }

    return recurse(startNode, setOf(startNode), 0).toString()
}

private class Node(r: Int, c: Int, val v: Char) : GraphNode(r, c)