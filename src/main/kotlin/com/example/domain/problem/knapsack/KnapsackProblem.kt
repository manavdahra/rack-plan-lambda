package com.example.domain.problem.knapsack

import io.jenetics.*
import io.jenetics.engine.*
import io.jenetics.util.ISeq
import java.util.function.Function
import java.util.random.RandomGenerator


class KnapsackProblem(itemCount: Int, randomGen: RandomGenerator) : Problem<ISeq<Item>, BitGene, Double> {

    private var items: ISeq<Item> = ISeq.of(
        generateSequence {
            Item(randomGen.nextDouble(100.0), randomGen.nextDouble(25.0))
        }.take(itemCount).toList()
    )
    private var capacity: Double = items.sumOf { it.weight } / 3
    private var codec: Codec<ISeq<Item>, BitGene> = Codecs.ofSubSet(this.items)

    override fun fitness(): Function<ISeq<Item>, Double> {
        return Function<ISeq<Item>, Double> { items ->
            val totalValue = items.sumOf { it.value }
            val totalWeight = items.sumOf { it.weight }
            if (totalWeight <= capacity) totalValue else 0.0
        }
    }

    override fun codec(): Codec<ISeq<Item>, BitGene> = this.codec

    fun print() {
        println()
        println("############## KnapSack Problem ####################")
        println("Items")
        println(items.joinToString("\n"))
        println()
        println("max capacity: $capacity")
        println()
    }
}

