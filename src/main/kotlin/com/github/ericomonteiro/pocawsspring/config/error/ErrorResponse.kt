package com.github.ericomonteiro.pocawsspring.config.error

import com.fasterxml.jackson.annotation.JsonAutoDetect
import org.springframework.http.HttpStatus

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class ErrorResponse(
    val statusCode: Int,
    val errors: List<ApiError>
) {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    class ApiError(
        val code: String,
        val message: String
    )


    companion object {
        fun of(status: HttpStatus, errors: List<ApiError>): ErrorResponse {
            return ErrorResponse(status.value(), errors)
        }

        fun of(status: HttpStatus, error: ApiError): ErrorResponse {
            return of(status, listOf(error))
        }
    }
}
