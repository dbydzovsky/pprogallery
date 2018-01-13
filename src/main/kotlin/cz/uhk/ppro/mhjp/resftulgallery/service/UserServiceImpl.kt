package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dao.RoleRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import cz.uhk.ppro.mhjp.resftulgallery.security.AuthorizationManager
import cz.uhk.ppro.mhjp.resftulgallery.security.PasswordValidator
import cz.uhk.ppro.mhjp.resftulgallery.util.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository,
        private val authorizationManager: AuthorizationManager,
        private val passwordValidator: PasswordValidator,
        private val dtoValidator: DtoValidator,
        private val responseBuilder: ResponseBuilder,
        private val dtoBuilder: DtoBuilder
) : UserService {

    override fun createEntity(newType: NewUserDto, authorization: String?, parent: String?): ResponseEntity<ResponseDto> {
        dtoValidator.validateDto(newType)
        val existingUser = userRepository.getOneByUsername(newType.username)
        if (existingUser != null)
            throw UsernameAlreadyExistsException("Error while creating new user. Username '${newType.username}' already exists.")
        if (newType.password != newType.repeatPassword)
            throw PasswordsDontMatchException("Error while creating new user. Passwords don't match.")
        val newUser = User(
                username = newType.username,
                name = newType.username,
                password = passwordValidator.hashPassword(newType.password),
                roles = roleRepository.findByName("ROLE_USER")
        )
        userRepository.save(newUser)
        val dto = dtoBuilder.getUserDto(newUser)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully created new user '${newUser.username}'", dto)
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        val user = userRepository.getOneByUsername(idType) ?:
                throw ContentNotFoundException("Error while retrieving user. User '$idType' not found.")
        if (user.private) authorizationManager.authorize(authorization, idType)
        val dto = dtoBuilder.getUserDto(user)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully retrieved user '$idType'.", dto)
    }

    override fun updateEntity(idType: String, updateType: UpdateUserDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(authorization, idType)
        val userToBeEdited = userRepository.getOneByUsername(idType) ?:
                throw ContentNotFoundException("Error while editing user. User '$idType' not found.")
        dtoValidator.validateDto(updateType)
        val existingUser = userRepository.getOneByUsername(updateType.username)
        if (existingUser != null) throw UsernameAlreadyExistsException("Error while editing user. Username '$idType' already exists.")
        val editedUser = userToBeEdited.copy(
                username = updateType.username,
                name = updateType.name,
                private = updateType.private
        )
        userRepository.save(editedUser)
        val dto = dtoBuilder.getUserDto(editedUser)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully edited user '$idType'.", dto)
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(authorization, idType)
        val user = userRepository.getOneByUsername(idType) ?:
                throw ContentNotFoundException("Error while deleting user. User '$idType' not found.")
        userRepository.delete(user)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully deleted user '$idType'.")
    }

    override fun changePassword(username: String, updateUserPasswordDto: UpdateUserPasswordDto, authorization: String): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(authorization, username)
        val user = userRepository.getOneByUsername(username) ?:
                throw ContentNotFoundException("Error while updating passwords. User '$username' not found.")
        dtoValidator.validateDto(updateUserPasswordDto)
        if (updateUserPasswordDto.newPassword != updateUserPasswordDto.repeatNewPassword)
            throw PasswordsDontMatchException("Error while updating passwords. New passwords don't match.")
        if (!passwordValidator.matchPasswords(updateUserPasswordDto.newPassword, user.password))
            throw PasswordsDontMatchException("Error while updating passwords. New and old passwords don't match.")
        val updatedUser = user.copy(
                password = passwordValidator.hashPassword(updateUserPasswordDto.newPassword)
        )
        userRepository.save(updatedUser)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully changed password of user '$username'.")
    }

    override fun disableUser(username: String, authorization: String): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(token = authorization, specifiedRoles = listOf("ROLE_MODERATOR"))
        val user = userRepository.getOneByUsername(username) ?:
                throw ContentNotFoundException("Error while disabling user. User '$username' not found.")
        if (!user.enabled) throw NothingToDoException("Error while disabling user. User is already disabled.")
        val disabledUser = user.copy(enabled = false)
        userRepository.save(disabledUser)
        return responseBuilder.buildSuccessfulResponse(204, "User '$username' successfully disabled.")
    }

    override fun enableUser(username: String, authorization: String): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(token = authorization, specifiedRoles = listOf("ROLE_MODERATOR"))
        val user = userRepository.getOneByUsername(username) ?:
                throw ContentNotFoundException("Error while enabling user. User '$username' not found.")
        if (user.enabled)
            throw NothingToDoException("Error while enabling user. User is already enabled.")
        val enabledUser = user.copy(enabled = true)
        userRepository.save(enabledUser)
        return responseBuilder.buildSuccessfulResponse(204, "User '$username' successfully enabled.")
    }

    override fun getUserRoles(username: String, authorization: String): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(authorization)
        val user = userRepository.getOneByUsername(username)
                ?: throw ContentNotFoundException("Error while getting $username's roles. User '$username' not found.")
        val roles = dtoBuilder.getRolesDto(user.roles)
        return responseBuilder.buildSuccessfulResponse(200, "User '$username' roles successfully retrieved.", roles)
    }

    override fun updateUserRoles(username: String, authorization: String, roles: List<String>): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(authorization)
        val user = userRepository.getOneByUsername(username)
                ?: throw ContentNotFoundException("Error while changing $username's roles. User '$username' not found.")
        val rolesToBeAdded = roles.mapNotNull { roleRepository.getOneByName(it) }
        if (rolesToBeAdded.isEmpty()) throw NoContentException("Error while changing $username's roles. There are no roles.")
        val updatedUser = user.copy(roles = rolesToBeAdded)
        userRepository.save(updatedUser)
        return responseBuilder.buildSuccessfulResponse(204, "User '$username' roles updated successfully.")
    }
}