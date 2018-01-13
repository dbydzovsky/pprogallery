package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.service.AuthenticationService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class AuthenticationController(
        private val authenticationService: AuthenticationService,
        private val headerUtil: HeaderUtil
) {

    @PostMapping("/api/auth/token")
    fun getToken(request: HttpServletRequest) = authenticationService.createJwtFromCredentials(headerUtil.getTokenFromHeader(request))

    @GetMapping("/api/auth/token")
    fun getTokenOwner(request: HttpServletRequest) = authenticationService.getTokenUser(headerUtil.getTokenFromHeader(request))

}