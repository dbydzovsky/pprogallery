package cz.uhk.ppro.mhjp.resftulgallery.util

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class HeaderUtil {
    fun getTokenFromHeader(request: HttpServletRequest): String = request.getHeader("Authorization")
            ?: throw JwtMissingException("Unauthorized. Your token is missing.")

    fun getOptionalTokenFromHeader(request: HttpServletRequest): String? = request.getHeader("Authorization")
}