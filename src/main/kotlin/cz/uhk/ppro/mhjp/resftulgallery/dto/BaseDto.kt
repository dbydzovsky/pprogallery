package cz.uhk.ppro.mhjp.resftulgallery.dto

import org.springframework.hateoas.ResourceSupport

// Data DTOs -> these DTOs hold response data

interface DataDtoBase

open class DataDto : DataDtoBase, ResourceSupport()

open class DataDtoWithoutLinks : DataDtoBase

// Response DTOs -> these DTOs contains additional response info

open class ResponseDto(
        val responseCode: Int,
        val success: Boolean
)

class SuccessResponseDto(
        responseCode: Int,
        val message: String,
        val data: DataDtoBase?
) : ResponseDto(responseCode, true)

class ErrorResponseDto(
        responseCode: Int,
        val message: String
) : ResponseDto(responseCode, false)