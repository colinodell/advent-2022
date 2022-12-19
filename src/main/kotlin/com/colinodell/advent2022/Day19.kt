package com.colinodell.advent2022

class Day19(input: List<String>) {
    private val blueprints = input.map {
        val tokens = it.split(" ")
        mapOf(
            MaterialType.ORE to Materials(tokens[6].toInt(), 0, 0, 0),
            MaterialType.CLAY to Materials(tokens[12].toInt(), 0, 0, 0),
            MaterialType.OBSIDIAN to Materials(tokens[18].toInt(), tokens[21].toInt(), 0, 0),
            MaterialType.GEODE to Materials(tokens[27].toInt(), 0, tokens[30].toInt(), 0),
        )
    }

    fun solvePart1() = blueprints.mapIndexed { i, b -> calculateMaxGeodesWithBlueprint(b, 24) * (i + 1) }.sum()

    fun solvePart2() = blueprints.take(3).productOf { calculateMaxGeodesWithBlueprint(it, 32) }

    enum class MaterialType {
        ORE,
        CLAY,
        OBSIDIAN,
        GEODE
    }

    /**
     * Basically a map of material type to count
     */
    data class Materials(var ore: Int = 0, var clay: Int = 0, var obsidian: Int = 0, var geode: Int = 0) {
        operator fun get(material: MaterialType) = when (material) {
            MaterialType.ORE -> ore
            MaterialType.CLAY -> clay
            MaterialType.OBSIDIAN -> obsidian
            MaterialType.GEODE -> geode
        }

        operator fun set(material: MaterialType, value: Int) = when (material) {
            MaterialType.ORE -> ore = value
            MaterialType.CLAY -> clay = value
            MaterialType.OBSIDIAN -> obsidian = value
            MaterialType.GEODE -> geode = value
        }
    }

    /**
     * Encapsulates the state of our recursive search
     */
    data class State(val materials: Materials, val robots: Materials, val time: Int = 0)

    private fun calculateMaxGeodesWithBlueprint(blueprint: Map<MaterialType, Materials>, maxTime: Int): Int {
        val initialState = State(
            // No materials to start
            Materials(),
            // One ore robot to start
            Materials(ore = 1)
        )

        // Limit the number of robots to the amount necessary to gather enough materials to build one more robot each turn
        val robotLimit = Materials(
            blueprint.values.maxOf { it[MaterialType.ORE] },
            blueprint.values.maxOf { it[MaterialType.CLAY] },
            blueprint.values.maxOf { it[MaterialType.OBSIDIAN] },
            // But don't limit geode robots
            Int.MAX_VALUE,
        )

        return solveRecursive(blueprint, initialState, maxTime, robotLimit, 0)
    }

    private fun solveRecursive(blueprint: Map<MaterialType, Materials>, state: State, maxTime: Int, robotLimit: Materials, maxGeodes: Int): Int {
        var maxGeodes = maxGeodes

        // Create a new branch for each robot we can create
        var robotRecipesToTry = blueprint.filterKeys { type -> state.robots[type] < robotLimit[type] }
        for ((robotType, recipe) in robotRecipesToTry) {
            // How long must we wait until we can create this new robot?
            val buildTime = 1 + MaterialType.values().maxOf { type ->
                when {
                    // We already have enough of this material to build the robot immediately, or we don't need this material at all (recipe[type] == 0)
                    recipe[type] <= state.materials[type] -> 0
                    // We don't have any robots that can collect this material yet
                    state.robots[type] == 0 -> maxTime + 1
                    // We have the necessary robots but not enough materials yet
                    else -> (recipe[type] - state.materials[type] + state.robots[type] - 1) / state.robots[type]
                }
            }

            // If we can't build this robot before we run out of time, skip it
            val timeUntilBuilt = state.time + buildTime
            if (timeUntilBuilt >= maxTime) {
                continue
            }

            // How many materials and robots would we have once this robot is built?
            val newMaterials = Materials()
            val newRobots = Materials()
            MaterialType.values().forEach { type ->
                newMaterials[type] = state.materials[type] + state.robots[type] * buildTime - recipe[type]
                newRobots[type] = state.robots[type] + (if (type == robotType) 1 else 0)
            }

            // Can we beat the current maximum if we only build geode robots from this point forwards?
            val remainingTime = maxTime - timeUntilBuilt
            val geodesWeCouldCollect = (((remainingTime - 1) * remainingTime) / 2) + newMaterials[MaterialType.GEODE] + (remainingTime * newRobots[MaterialType.GEODE])
            if (geodesWeCouldCollect < maxGeodes) {
                continue
            }

            // We'll need to build some other robots
            maxGeodes = solveRecursive(blueprint, State(newMaterials, newRobots, timeUntilBuilt), maxTime, robotLimit, maxGeodes)
        }

        return maxOf(maxGeodes, state.materials[MaterialType.GEODE] + state.robots[MaterialType.GEODE] * (maxTime - state.time))
    }
}
