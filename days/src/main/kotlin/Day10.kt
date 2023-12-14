package day10

import util.Graph
import util.GraphNode
import util.parseFromGrid

fun task1(input: List<String>): String {
    val graph = parse(input)
    val startNode = graph.find { it.pipe == Pipe.S }!!
    var maxDist = 0
    graph.bfs(startNode) { _, dist ->
        maxDist = maxOf(maxDist, dist)
    }
    return maxDist.toString()
}

fun task2Expand(input: List<String>): String {
    val graph = parse(input)
    val startNode = graph.find { it.pipe == Pipe.S }!!
    val visited: MutableList<Node> = mutableListOf()
    visited.add(startNode)
    graph.bfs(startNode) { n, _ ->
        visited.add(n)
    }

    val max_r = visited.maxOf { it.gridPos.first }
    val min_r = visited.minOf { it.gridPos.first }
    val max_c = visited.maxOf { it.gridPos.second }
    val min_c = visited.minOf { it.gridPos.second }

    val sb = StringBuilder()

    for (r in min_r..max_r) {
        val la: MutableList<String> = mutableListOf()
        val lb: MutableList<String> = mutableListOf()
        val lc: MutableList<String> = mutableListOf()
        for (c in min_c..max_c) {
            val n = visited.find { it.gridPos.first == r && it.gridPos.second == c }
            if (n == null) {
                la.add("III")
                lb.add("III")
                lc.add("III")
            } else {
                when (n.pipe) {
                    Pipe.S -> {
                        la.add("O|O")
                        lb.add("-+-")
                        lc.add("O|O")
                    }

                    Pipe.Vertical -> {
                        la.add("O|O")
                        lb.add("O+O")
                        lc.add("O|O")
                    }

                    Pipe.Horizontal -> {
                        la.add("OOO")
                        lb.add("-+-")
                        lc.add("OOO")
                    }

                    Pipe.L -> {
                        la.add("O|O")
                        lb.add("O+-")
                        lc.add("OOO")
                    }

                    Pipe.J -> {
                        la.add("O|O")
                        lb.add("-+O")
                        lc.add("OOO")
                    }

                    Pipe.Seven -> {
                        la.add("OOO")
                        lb.add("-+O")
                        lc.add("O|O")
                    }

                    Pipe.F -> {
                        la.add("OOO")
                        lb.add("O+-")
                        lc.add("O|O")
                    }

                    else -> {
                        la.add("III")
                        lb.add("III")
                        lc.add("III")
                    }
                }
            }
        }

        sb.appendLine(la.joinToString(""))
        sb.appendLine(lb.joinToString(""))
        sb.appendLine(lc.joinToString(""))
    }

    return sb.toString()
}

fun task2Color(input: List<String>): String {
    val sbs = input.map { StringBuilder(it) }

    val openSet = mutableListOf(Pair(0, 0))
    val closedSet: MutableSet<Pair<Int, Int>> = mutableSetOf()
    openSet.add(Pair(0, 0))
    val toVisitDelta = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    while (openSet.isNotEmpty()) {
        val (r, c) = openSet.removeAt(0)
        sbs[r][c] = 'O'

        for ((nr, nc) in toVisitDelta) {
            val thisR = r + nr
            val thisC = c + nc
            val p = Pair(thisR, thisC)
            if (thisR in sbs.indices
                && thisC in sbs[thisR].indices
                && (sbs[thisR][thisC] == 'I' || sbs[thisR][thisC] == 'O')
                && closedSet.add(p)
            ) {
                openSet.add(p)
            }
        }
    }
    val iCount = sbs.joinToString("").count { it == 'I' }
    return (iCount / 9).toString()
}

private fun parse(input: List<String>): Graph<Node> {
    return parseFromGrid(input,
        { r, c, v ->
            val type = when (v) {
                '|' -> Pipe.Vertical
                '-' -> Pipe.Horizontal
                'L' -> Pipe.L
                'J' -> Pipe.J
                '7' -> Pipe.Seven
                'F' -> Pipe.F
                '.' -> Pipe.None
                'S' -> Pipe.S
                else -> throw RuntimeException("Ups $v")
            }

            Node(Pair(r, c), type)
        },
        { n1, n2 ->
            if (n1.isWestOf(n2) && n1.pipe.east && n2.pipe.west) {
                true
            } else if (n1.isEastOf(n2) && n1.pipe.west && n2.pipe.east) {
                true
            } else if (n1.isNorthOf(n2) && n1.pipe.south && n2.pipe.north) {
                true
            } else if (n1.isSouthOf(n2) && n1.pipe.north && n2.pipe.south) {
                true
            } else {
                false
            }
        })
}

private data class Node(val gridPos: Pair<Int, Int>, val pipe: Pipe) : GraphNode(gridPos.first, gridPos.second) {
    fun isWestOf(n2: Node) = gridPos.second == n2.gridPos.second - 1
    fun isEastOf(n2: Node) = gridPos.second == n2.gridPos.second + 1
    fun isNorthOf(n2: Node) = gridPos.first == n2.gridPos.first - 1
    fun isSouthOf(n2: Node) = gridPos.first == n2.gridPos.first + 1
}

private enum class Pipe(val north: Boolean, val south: Boolean, val west: Boolean, val east: Boolean) {
    S(true, true, true, true),
    Vertical(true, true, false, false),
    Horizontal(false, false, true, true),
    L(true, false, false, true),
    J(true, false, true, false),
    Seven(false, true, true, false),
    F(false, true, false, true),
    None(false, false, false, false)
}