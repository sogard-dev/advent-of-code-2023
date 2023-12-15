package day15

fun task1(input: List<String>) = input.sumOf { hash(it) }.toString()

fun task2(input: List<String>): String {
    val map: HashMap<Int, MutableList<Lens>> = hashMapOf()
    (0..255).forEach { map[it] = mutableListOf() }

    input.forEach {
        val (label, focalStr) = it.split("-", "=")
        val hash = hash(label)
        val list = map[hash]!!

        val indexOfFirst = list.indexOfFirst { i -> i.label == label }
        if (indexOfFirst >= 0) {
            list.removeAt(indexOfFirst)
        }

        if (focalStr.isNotEmpty()) {
            val lens = Lens(label, focalStr.toInt(), hash)
            if (indexOfFirst >= 0) {
                list.add(indexOfFirst, lens)
            } else {
                list.add(lens)
            }
        }
    }

    return map.map { (k, v) -> v.withIndex().sumOf { (i, l) -> (1 + k) * (1 + i) * l.focal } }.sum().toString()
}


fun hash(it: String) = it.chars().reduce(0) { l, r -> ((l + r) * 17) % 256 }
private data class Lens(val label: String, val focal: Int, val hash: Int)