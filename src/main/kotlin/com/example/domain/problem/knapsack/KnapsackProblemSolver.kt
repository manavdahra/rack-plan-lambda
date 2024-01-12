package com.example.domain.problem.knapsack

import io.jenetics.Mutator
import io.jenetics.RouletteWheelSelector
import io.jenetics.SinglePointCrossover
import io.jenetics.TournamentSelector
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.engine.Limits
import java.util.*

object KnapsackProblemSolver {
    fun solve() {
        val knapsackProblem = KnapsackProblem(15, Random(123))
        knapsackProblem.print()

        val engine = Engine.builder(knapsackProblem)
            .populationSize(32)
            .survivorsSelector(TournamentSelector(2))
            .offspringSelector(RouletteWheelSelector())
            .alterers(
                Mutator(0.115),
                SinglePointCrossover(0.16),
            )
            .build()

        val stats = EvolutionStatistics.ofNumber<Double>()

        val result = engine.stream()
            // Truncate the evolution stream after 7 "steady" generations.
            .limit(Limits.bySteadyFitness(7))
            // The evolution will stop after maximal 100 generations.
            .limit(100)
            // Update the evaluation statistics after each generation
            .peek(stats)
            // Collect (reduce) the evolution stream to its best phenotype.
            .collect(EvolutionResult.toBestGenotype())

        val best = knapsackProblem.decode(result)
        println()
        println("############## Solution ####################")
        println("Items")
        println(best.joinToString("\n"))
        println()
        println("total weight: ${best.sumOf { it.weight }}")
        println()
        println("###########################################")
        println()
    }
}