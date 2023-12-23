package util


fun <N> parseFromGrid(input: List<String>, nodeCreator: (Int, Int, Char) -> N?, nodeConnector: (N, N) -> Boolean): Graph<N>
        where N : GraphNode {
    val nodes: MutableMap<Pair<Int, Int>, N> = mutableMapOf()

    input.withIndex().forEach { (r, line) ->
        line.withIndex().forEach { (c, v) ->
            nodeCreator(r, c, v)?.let {
                nodes[Pair(r, c)] = it
            }
        }
    }

    val connections: MutableMap<N, MutableList<N>> = mutableMapOf()
    nodes.forEach { (_, v) -> connections[v] = mutableListOf() }

    val toVisit = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
    nodes.forEach { (k, v) ->
        val myConnections = connections[v]!!
        toVisit.forEach { (dr, dc) ->
            nodes[Pair(k.first + dr, k.second + dc)]?.let {
                if (nodeConnector(v, it)) {
                    myConnections.add(it)
                }
            }
        }
    }

    return Graph(nodes.values.toList(), connections)
}

data class Graph<N>(private val nodes: List<N>, private val connections: Map<N, List<N>>)
        where N : GraphNode {

    fun find(finder: (N) -> Boolean): N? {
        return nodes.find(finder)
    }

    fun bfs(startNode: N, maxSteps: Int = 99999999, cb: (N, Int) -> Unit) {
        bfsUntil(startNode) { n, d ->
            cb(n, d)
            d < maxSteps
        }
    }

    fun bfsUntil(startNode: N, cb: (N, Int) -> Boolean) {
        val openSet = ArrayDeque(listOf(Pair(0, startNode)))
        val closedSet = mutableSetOf(startNode)

        while (openSet.isNotEmpty()) {
            val (distance, node) = openSet.removeAt(0)
            val thisConnections = connections[node]!!
            for (n in thisConnections) {
                if (closedSet.add(n)) {
                    val newDistance = distance + 1
                    if (cb(n, newDistance)) {
                        openSet.add(Pair(newDistance, n))

                    }
                }
            }
        }
    }

    fun nodesWithVisitor(): Iterable<Pair<N, Visitor<N>>> {
        return nodes.map {
            Pair(it, getVisitor(it))
        }
    }

    fun getVisitor(it: N) = object : Visitor<N> {
        override fun getUpNode(): N? {
            return connections[it]!!.find { f ->
                f.c == it.c && f.r == it.r - 1
            }
        }

        override fun getDownNode(): N? {
            return connections[it]!!.find { f ->
                f.c == it.c && f.r == it.r + 1
            }
        }

        override fun getRightNode(): N? {
            return connections[it]!!.find { f ->
                f.c == it.c + 1 && f.r == it.r
            }
        }

        override fun getLeftNode(): N? {
            return connections[it]!!.find { f ->
                f.c == it.c - 1 && f.r == it.r
            }
        }
    }

    fun print(formatter: (N) -> String) {
        for (r in nodes.minOf { it.r }..nodes.maxOf { it.r }) {
            for (c in nodes.minOf { it.c }..nodes.maxOf { it.c }) {
                val n = find { it.r == r && it.c == c }
                if (n != null) {
                    print(formatter(n))
                } else {
                    print("?")
                }
            }
            println()
        }
    }

    fun nodes(): Iterable<N> = nodes
    fun connections(n: N) = connections[n]!!
    fun getHeight() = nodes.maxOf { it.r }
    fun getWidth() = nodes.maxOf { it.c }
    fun topLeftNode() = nodes.find { it.r == 0 && it.c == 0 }
    fun bottomRightNode() = nodes.find { f -> f.r == nodes.maxOf { it.r } && f.c == nodes.maxOf { it.c } }
}

interface Visitor<N> where N : GraphNode {
    fun getUpNode(): N?
    fun getDownNode(): N?
    fun getRightNode(): N?
    fun getLeftNode(): N?
    fun getAll(): List<N> {
        return listOfNotNull(getUpNode(), getDownNode(), getLeftNode(), getRightNode())
    }
}

abstract class GraphNode(val r: Int, val c: Int) {

    fun isLeftOf(n2: GraphNode) = r == n2.r && c == n2.c - 1
    fun isRightOf(n2: GraphNode) = r == n2.r && c == n2.c + 1
    fun isAbove(n2: GraphNode) = r == n2.r - 1 && c == n2.c


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GraphNode

        if (r != other.r) return false
        if (c != other.c) return false

        return true
    }

    override fun hashCode(): Int {
        var result = r
        result = 31 * result + c
        return result
    }
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN;

    fun opposite() = when (this) {
        LEFT -> RIGHT
        RIGHT -> LEFT
        UP -> DOWN
        DOWN -> UP
    }
}