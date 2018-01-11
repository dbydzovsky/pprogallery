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
        private val hateoasUtil: HateoasUtil
) : UserService {

    override fun createEntity(newType: NewUserDto, authorization: String?): ResponseEntity<ResponseDto> {
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
        val dto = UserDataDto(newUser.username, newUser.name, newUser.dateJoined, newUser.enabled, newUser.private)
        val dtoWithLinks = hateoasUtil.addSelfObjectLink(dto)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully created new user '${newUser.username}'", dtoWithLinks)
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        val user = userRepository.getOneByUsername(idType) ?:
                throw ContentNotFoundException("Error while retrieving user. User '$idType' not found.")
        if (user.private) authorizationManager.authorize(authorization, idType)
        val dto = UserDataDto(user.username, user.name, user.dateJoined, user.enabled, user.private)
        val dtoWithLinks = hateoasUtil.addSelfObjectLink(dto)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully retrieved user '$idType'.", dtoWithLinks)
    }

    override fun updateEntity(idType: String, updateType: UpdateUserDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        if (authorize) authorizationManager.authorize(authorization, idType)
        val editingUser = userRepository.getOneByUsername(idType) ?:
                throw ContentNotFoundException("Error while editing user. User '$idType' not found.")
        dtoValidator.validateDto(updateType)
        val existingUser = userRepository.getOneByUsername(updateType.username)
        if (existingUser != null) throw UsernameAlreadyExistsException("Error while editing user. Username '$idType' already exists.")
        val editedUser = editingUser.copy(
                username = updateType.username,
                name = updateType.name,
                private = updateType.private
        )
        userRepository.save(editedUser)
        val dto = UserDataDto(editedUser.username, editedUser.name, editedUser.dateJoined, editedUser.enabled, editedUser.private)
        val dtoWithLinks = hateoasUtil.addSelfObjectLink(dto)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully edited user '$idType'.", dtoWithLinks)
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        if (authorize) authorizationManager.authorize(authorization, idType)
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

    override fun reportUser(username: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto> {
        authorizationManager.authorize(token = authorization, specifiedRoles = listOf("ROLE_USER", "ROLE_MODERATOR"))
        val user = userRepository.getOneByUsername(username) ?:
                throw ContentNotFoundException("Error while reporting user. User '$username' not found.")
        dtoValidator.validateDto(report)
        // todo implement
        return responseBuilder.buildSuccessfulResponse(204, "Successfully reported user '$username' for '${report.reason}'.")
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
        val roles = user.roles.map { RoleDto(it.name) }
        return responseBuilder.buildSuccessfulResponse(200, "User '$username' roles successfully retrieved.", *roles.toTypedArray())
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