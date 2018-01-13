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
        private val responseBuilder: ResponseBuilder,
        private val dtoBuilder: DtoBuilder
) : AuthenticationService {

    override fun createJwtFromCredentials(authorization: String): ResponseEntity<ResponseDto> {
        val credentialsB64 = authorization.replace("Basic ", "")
        val credentials = try {
            String(Base64.getDecoder().decode(credentialsB64))
        } catch (e: IllegalArgumentException) {
            throw IncorrectAuthHeaderFormatException("Error during authentication. Authorization header is not valid Base64.")
        }

        val (username, password) = try {
            credentials.split(":")
        } catch (e: IndexOutOfBoundsException) {
            throw IncorrectAuthHeaderFormatException("Error during authentication. Authorization header has incorrect format.")
        }

        val user = userRepository.getOneByUsername(username)
                ?: throw ContentNotFoundException("Error during authentication. User '$username' not found.")

        if (!passwordValidator.matchPasswords(password, user.password))
            throw PasswordsDontMatchException("Error during authentication. Incorrect password")

        if (!user.enabled) throw ForbiddenContentException("Error during authentication. Your account is disabled.")

        val (token, expires) = jwtHandler.generateToken(username)

        return responseBuilder.buildSuccessfulResponse(200, "Successfully authenticated.", JwtDto(token, expires))

    }

    override fun getTokenUser(authorization: String): ResponseEntity<ResponseDto> {
        val authenticatedUser = userRepository.getOneByUsername(jwtHandler.validateToken(authorization))
                ?: throw TokenOwnerNotFoundException("Error during user identification. Token owner not found.")
        val dto = dtoBuilder.getListUserDto(authenticatedUser, "self")
        return responseBuilder.buildSuccessfulResponse(200, "Successfully retrieved token owner.", dto)
    }
}