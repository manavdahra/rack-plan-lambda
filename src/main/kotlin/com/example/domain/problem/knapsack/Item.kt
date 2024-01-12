package com.example.domain.problem.knapsack

data class Item(val value: Double, val weight: Double) {
    override fun toString(): String {
        return "(Value: $value, Weight: $weight)"
    }
}