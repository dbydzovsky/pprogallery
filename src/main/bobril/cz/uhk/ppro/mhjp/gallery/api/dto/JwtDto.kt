package cz.uhk.ppro.mhjp.resftulgallery.dto

open class JwtDto(
        val token: String,
        val expires: Int
) : DataDtoWithoutLinks()