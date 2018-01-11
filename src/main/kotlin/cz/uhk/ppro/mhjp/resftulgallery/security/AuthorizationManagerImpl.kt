package cz.uhk.ppro.mhjp.resftulgallery.security

import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.util.ForbiddenContentException
import cz.uhk.ppro.mhjp.resftulgallery.util.JwtMissingException
import cz.uhk.ppro.mhjp.resftulgallery.util.TokenOwnerNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthorizationManagerImpl(private val jwtHandler: JwtHandler,
                               private val userRepository: UserRepository) : AuthorizationManager {

    private val defaultRoles = listOf("ROLE_ADMIN")

    override fun authorize(token: String?, entityOwner: String?, specifiedRoles: List<String>) {
        if (token == null) throw JwtMissingException("Unauthorized. Your token is missing.")

        val userReq = userRepository.getOneByUsername(jwtHandler.validateToken(token))
                ?: throw TokenOwnerNotFoundException("Unauthorized. Token owner not found.")

        val finalRoles = defaultRoles.plus(specifiedRoles)
        finalRoles.distinct()

        if (!Collections.disjoint(userReq.roles.map { it.name }, finalRoles)) return

        if (entityOwner != userReq.username)
            throw ForbiddenContentException("Forbidden. You don't have rights to do this action.")

//        val finalRoles = defaultRoles.plus(specifiedRoles)
//        finalRoles.distinct()
//
//        if (Collections.disjoint(userReq.roles.map { it.name }, finalRoles))
//            throw ForbiddenContentException("Forbidden. You don't have rights to do this action.")
    }
}