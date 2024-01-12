package com.example.controller

import com.example.domain.problem.knapsack.KnapsackProblemSolver
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Serdeable
data class RackPlanRepresentation(val id: String, val name: String)

@Controller("/rack-planning")
class RackPlanController {
    private val rackPlansRepresentation = mutableMapOf<String, RackPlanRepresentation>()

    @Get("/rack-plans/{rackPlanId}")
    @Produces("application/json")
    fun getRackPlans(rackPlanId: String): HttpResponse<*> {
        val rackPlan = rackPlansRepresentation[rackPlanId] ?: throw RackPlanNotFoundException("Rack Plan not found")
        return HttpResponse.ok(rackPlan)
    }

    @Post("/rack-plans")
    @Produces("application/json")
    fun createRackPlan(): HttpResponse<*> {
        val id = UUID.randomUUID()
        val newRackPlanRepresentation =
            RackPlanRepresentation(id.toString(), "Rack Plan: ${rackPlansRepresentation.size}")
        rackPlansRepresentation[id.toString()] = newRackPlanRepresentation
        KnapsackProblemSolver.solve()
        return HttpResponse.created(newRackPlanRepresentation)
    }
}
