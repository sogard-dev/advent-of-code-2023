package day8

import util.lcm

fun task1(input: List<String>): String {
    val network = parse(input)

    var position = "AAA"
    var steps = 0
    while (position != "ZZZ") {
        val current = network.nodes.first { it.name == position }
        for (instruction in network.instructions) {
            steps++

            when (instruction) {
                'R' -> position = current.right
                'L' -> position = current.left
            }
        }
    }
    return steps.toString()
}

fun task2(input: List<String>): String {
    val network = parse(input)
    val positions = network.nodes.filter { it.name.endsWith("A") }
    val solutionLengths = positions.map { part2(it, network) }
    return lcm(*solutionLengths.toLongArray()).toString()
}

private fun part2(init: Node, network: Network): Long {
    var p = init
    var steps = 0L
    while (!p.name.endsWith("Z")) {
        for (instruction in network.instructions) {
            steps++

            p = when (instruction) {
                'R' -> p.rightNode!!
                'L' -> p.leftNode!!
                else -> throw RuntimeException("hmm")
            }

        }
    }
    return steps
}

private fun parse(input: List<String>): Network {
    val instructions = input[0]
    val text = Regex("(\\w+)")

    val nodes = (2 until input.size).map {
        val (name, left, right) = text.findAll(input[it]).map { r -> r.groups[1]!!.value }.toList()
        Node(name, left, right, null, null)
    }

    nodes.forEach {
        it.rightNode = nodes.first { n -> n.name == it.right }
        it.leftNode = nodes.first { n -> n.name == it.left }
    }

    return Network(instructions, nodes)
}

private data class Network(val instructions: String, val nodes: List<Node>)
private data class Node(val name: String, val left: String, val right: String, var leftNode: Node?, var rightNode: Node?)