package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

interface AuthenticationService {

    fun createJwtFromCredentials(request: HttpServletRequest): ResponseEntity<ResponseDto>

}