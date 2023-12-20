package day20


private const val ON = true
private const val OFF = false
private const val HIGH = true
private const val LOW = false

fun task1(input: List<String>, buttonPushes: Int): String {
    val (_, countLow, countHigh) = solve(input, buttonPushes)
    return (countLow * countHigh).toString()
}

fun task2(input: List<String>): String {
    val (c, _) = solve(input, 10000)
    return c.toString()
}

private fun solve(input: List<String>, buttonPushes: Int): Triple<Int, Long, Long> {
    val system: Map<String, Module> = parse(input)

    val pulses: MutableList<Pair<String, Pulse>> = mutableListOf()

    var countLow = 0L
    var countHigh = 0L

    repeat(buttonPushes) { step ->
        pulses.add("button" to Pulse(LOW, "broadcaster"))

        while (pulses.isNotEmpty()) {
            val (from, pulse) = pulses.removeAt(0)
            if (pulse.pulse == HIGH) {
                countHigh++
            } else {
                countLow++
            }

            if (pulse.target == "vf" && pulse.pulse == HIGH) {
                println("$step : $from sent ${pulseToString(pulse.pulse)} to vf")
            }

            val module = system[pulse.target]
            if (module != null) {
                val newPulses = module.evaluate(from, pulse)
                pulses.addAll(newPulses.map { p -> pulse.target to p })
            }
        }
    }

    return Triple(buttonPushes, countLow, countHigh)
}

private fun parse(input: List<String>): Map<String, Module> {
    val map = input.associate {
        val (k, t) = it.split(" -> ")
        val targets = t.split(",").map { s -> s.trim() }
        when {
            k.startsWith("%") -> k.substring(1) to FlipFlop(targets)
            k.startsWith("&") -> k.substring(1) to Conjunction(targets)
            else -> k to Broadcaster(targets)
        }
    }

    map.forEach {
        val con = it.value
        if (con is Conjunction) {
            val hasThisTarget = map.filter { f ->
                f.value.getModuleTargets().contains(it.key)
            }.map { f ->
                f.key
            }
            con.setInputs(hasThisTarget)
        }
    }

    return map
}

private data class Pulse(val pulse: Boolean, val target: String)


private interface Module {
    fun evaluate(from: String, p: Pulse): List<Pulse>
    fun getModuleTargets(): List<String>
}

private data class Conjunction(val targets: List<String>) : Module {
    private val state: MutableMap<String, Boolean> = mutableMapOf()

    override fun evaluate(from: String, p: Pulse): List<Pulse> {
        state[from] = p.pulse

        return if (state.values.all { it == HIGH }) {
            targets.map { Pulse(LOW, it) }
        } else {
            targets.map { Pulse(HIGH, it) }
        }
    }

    override fun getModuleTargets() = targets

    fun setInputs(l: List<String>) {
        l.forEach {
            state[it] = LOW
        }
    }
}

private data class FlipFlop(val targets: List<String>) : Module {
    private var state = false

    override fun evaluate(from: String, p: Pulse): List<Pulse> {
        if (p.pulse == LOW) {
            state = !state

            return if (state == ON) {
                targets.map { Pulse(HIGH, it) }
            } else {
                targets.map { Pulse(LOW, it) }
            }
        }
        return listOf()
    }

    override fun getModuleTargets() = targets

}

private data class Broadcaster(val targets: List<String>) : Module {
    override fun evaluate(from: String, p: Pulse): List<Pulse> {
        return targets.map { Pulse(p.pulse, it) }
    }

    override fun getModuleTargets() = targets
}


private fun pulseToString(pulse: Boolean) = if (pulse == HIGH) {
    "high"
} else {
    "low"
}