package util


fun <N> parseFromGrid(input: List<String>, nodeCreator: (Int, Int, Char) -> N, nodeConnector: (N, N) -> Boolean): Graph<N> {
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

data class Graph<N>(val nodes: List<N>, val connections: Map<N, List<N>>) {
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
}