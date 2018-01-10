package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @PostMapping("/auth/token")
    fun getToken(request: HttpServletRequest) = authenticationService.createJwtFromCredentials(request)

}