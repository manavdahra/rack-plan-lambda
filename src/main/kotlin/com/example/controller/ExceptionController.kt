package com.example.controller

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [CustomException::class, Exception::class])
class ExceptionController : ExceptionHandler<CustomException?, HttpResponse<ErrorResponse>> {
    override fun handle(request: HttpRequest<*>?, exception: CustomException?): HttpResponse<ErrorResponse> {
        return when (exception) {
            is RackPlanNotFoundException -> HttpResponse.notFound(
                ErrorResponse(
                    exception.message ?: "",
                    HttpStatus.NOT_FOUND.code,
                )
            )

            else -> HttpResponse.serverError(
                ErrorResponse(
                    "Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR.code
                )
            )
        }
    }
}
