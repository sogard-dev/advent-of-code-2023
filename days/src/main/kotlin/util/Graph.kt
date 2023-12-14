package util


fun <N> parseFromGrid(input: List<String>, nodeCreator: (Int, Int, Char) -> N, nodeConnector: (N, N) -> Boolean): Graph<N>
        where N : GraphNode {
    val nodes: MutableMap<Pair<Int, Int>, N> = mutableMapOf()

    input.withIndex().forEach { (r, line) ->
        line.withIndex().forEach { (c, v) ->
            nodes[Pair(r, c)] = nodeCreator(r, c, v)
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

    fun bfs(startNode: N, cb: (N, Int) -> Unit) {
        val openSet = ArrayDeque(listOf(Pair(0, startNode)))
        val closedSet = mutableSetOf(startNode)

        while (openSet.isNotEmpty()) {
            val (distance, node) = openSet.removeAt(0)
            val thisConnections = connections[node]!!

            for (n in thisConnections) {
                if (closedSet.add(n)) {
                    openSet.add(Pair(distance + 1, n))
                    cb(n, distance + 1)
                }
            }
        }
    }

    fun nodesWithVisitor(): Iterable<Pair<N, Visitor<N>>> {
        return nodes.map {
            Pair(it, creatorVisitor(it))
        }
    }

    fun nodes(): Iterable<N> {
        return nodes
    }

    private fun creatorVisitor(it: N) = object : Visitor<N> {
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

    fun getHeight() = nodes.maxOf { it.r }
}

interface Visitor<N> where N : GraphNode {
    fun getUpNode(): N?
    fun getDownNode(): N?
    fun getRightNode(): N?
    fun getLeftNode(): N?
}

abstract class GraphNode(val r: Int, val c: Int)