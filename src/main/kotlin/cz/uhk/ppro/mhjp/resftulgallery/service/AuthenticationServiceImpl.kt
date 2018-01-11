package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.dto.JwtDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.security.JwtHandler
import cz.uhk.ppro.mhjp.resftulgallery.security.PasswordValidator
import cz.uhk.ppro.mhjp.resftulgallery.util.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class AuthenticationServiceImpl (
        private val userRepository: UserRepository,
        private val jwtHandler: JwtHandler,
        private val passwordValidator: PasswordValidator,
        private val responseBuilder: ResponseBuilder
) : AuthenticationService {

    override fun createJwtFromCredentials(request: HttpServletRequest): ResponseEntity<ResponseDto> {
        val basic = request.getHeader("Authorization")
                ?: throw NoAuthHeaderException("Error during token generation. Authorization header not found.")

        val credentialsB64 = basic.replace("Basic ", "")
        val credentials = try {
            String(Base64.getDecoder().decode(credentialsB64))
        } catch (e: IllegalArgumentException) {
            throw IncorrectAuthHeaderFormatException("Error during token generation. Authorization header is not valid Base64.")
        }

        val (username, password) = try {
            credentials.split(":")
        } catch (e: IndexOutOfBoundsException) {
            throw IncorrectAuthHeaderFormatException("Error during token generation. Authorization header has incorrect format.")
        }

        val user = userRepository.getOneByUsername(username)
                ?: throw ContentNotFoundException("Error during token generation. User '$username' not found.")

        if (!passwordValidator.matchPasswords(password, user.password))
            throw PasswordsDontMatchException("Error during token generation. Incorrect password")

        val (token, expires) = jwtHandler.generateToken(username)

        return responseBuilder.buildSuccessfulResponse(200, "Token successfully generated.", JwtDto(token, expires))

    }

}