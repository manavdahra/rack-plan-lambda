package com.example.controller

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorResponse(val message: String, val status: Int)
