package day25

fun task1(input: List<String>): String {
    val cut = listOf("bqp" to "fqr", "fhv" to "zsp", "hcd" to "cnr")

    val map: MutableMap<String, MutableList<String>> = mutableMapOf()
    input.forEach { l ->
        val split = l.replace(":", "").split(" ")
        for (element in split) {
            map[element] = mutableListOf()
        }
    }

    input.forEach { l ->
        val split = l.replace(":", "").split(" ")
        val a = split[0]
        for (i in 1 until split.size) {
            val b = split[i]
            if (!cut.contains(a to b) && !cut.contains(b to a)) {
                map[a]!!.add(b)
                map[b]!!.add(a)
            }
        }
    }

    val unvisitedVisit = map.keys.toMutableList()
    val clusterSizes: MutableList<Int> = mutableListOf()

    while (unvisitedVisit.isNotEmpty()) {
        val init = unvisitedVisit.removeFirst()
        val openSet = mutableListOf(init)
        val closedSet = mutableSetOf(init)
        while (openSet.isNotEmpty()) {
            val head = openSet.removeFirst()
            unvisitedVisit.remove(head)
            map[head]!!.forEach {
                if (closedSet.add(it)) {
                    openSet.add(it)
                }
            }
        }

        clusterSizes.add(closedSet.size)
    }

    val (a, b) = clusterSizes

    return (a * b).toString()
}


fun task2(input: List<String>): String {
    return ""
}