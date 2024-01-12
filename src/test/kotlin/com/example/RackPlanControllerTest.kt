package com.example

import com.example.controller.ErrorResponse
import com.example.controller.RackPlanRepresentation
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

@MicronautTest
class RackPlanControllerTest(@Client("/") val client: HttpClient) {

    private fun createRackPlan(): RackPlanRepresentation {
        val request = HttpRequest.POST<Any>("/rack-planning/rack-plans", "")
        val response = client.toBlocking().exchange(request, RackPlanRepresentation::class.java)
        assertEquals(201, response.code())
        assertNotNull(response.body())
        return response.body()
    }

    @Test
    fun `should get rack plan when it already exists`() {
        val newRackPlan = createRackPlan()
        val request = HttpRequest.GET<Any>("/rack-planning/rack-plans/${newRackPlan.id}")
        val response = client.toBlocking().exchange(request, RackPlanRepresentation::class.java)
        assertEquals(200, response.code())
        assertEquals(newRackPlan, response.body())
    }

    @Test
    fun `should return 404 when rack plan doesnt exist`() {
        val request = HttpRequest.GET<Any>("/rack-planning/rack-plans/${UUID.randomUUID()}")
        try {
            client.toBlocking().exchange(request, ErrorResponse::class.java)
        } catch (e: HttpClientResponseException) {
            assertEquals(404, e.status.code)
            assertEquals("Rack Plan not found", e.message)
        }
    }
}