package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ResponseBuilder {
    fun buildErrorResponse(responseCode: Int, errorMessage: String, headers: List<Pair<String, String>> = emptyList()): ResponseEntity<ResponseDto> {
        return if (headers.isNotEmpty()) {
            val httpHeaders = HttpHeaders()
            headers.forEach { httpHeaders.add(it.first, it.second) }
            ResponseEntity
                    .status(responseCode)
                    .headers(httpHeaders)
                    .body(ErrorResponseDto(
                            responseCode = responseCode,
                            message = errorMessage
                    ))
        } else {
            ResponseEntity
                    .status(responseCode)
                    .body(ErrorResponseDto(
                            responseCode = responseCode,
                            message = errorMessage
                    ))
        }
    }

    fun buildSuccessfulResponse(responseCode: Int, successMessage: String, data: DataDtoBase? = null, headers: List<Pair<String, String>> = emptyList()): ResponseEntity<ResponseDto> {
        return if (headers.isNotEmpty()) {
            val httpHeaders = HttpHeaders()
            headers.forEach { httpHeaders.add(it.first, it.second) }
            ResponseEntity
                    .status(responseCode)
                    .headers(httpHeaders)
                    .body(SuccessResponseDto(
                            responseCode = responseCode,
                            message = successMessage,
                            data = data
                    ))
        } else {
            ResponseEntity
                    .status(responseCode)
                    .body(SuccessResponseDto(
                            responseCode = responseCode,
                            message = successMessage,
                            data = data
                    ))
        }
    }
}