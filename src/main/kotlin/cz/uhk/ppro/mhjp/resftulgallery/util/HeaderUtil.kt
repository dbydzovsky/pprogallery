package cz.uhk.ppro.mhjp.resftulgallery.util

import javax.servlet.http.HttpServletRequest

fun getTokenFromHeader(request: HttpServletRequest): String = request.getHeader("Authorization")
        ?: throw JwtMissingException("Unauthorized. Your token is missing.")

fun getOptionalTokenFromHeader(request: HttpServletRequest): String? = request.getHeader("Authorization")