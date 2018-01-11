package cz.uhk.ppro.mhjp.resftulgallery.dto

import org.springframework.hateoas.ResourceSupport

interface DataDtoBase

open class DataDto : DataDtoBase, ResourceSupport()

open class DataDtoWithoutLinks : DataDtoBase

open class ResponseDto(
        val responseCode: Int,
        val success: Boolean
)

class SuccessResponseDto(
        responseCode: Int,
        val message: String,
        val data: List<DataDtoBase>
) : ResponseDto(responseCode, true)

class ErrorResponseDto(
        responseCode: Int,
        val message: String
) : ResponseDto(responseCode, false)