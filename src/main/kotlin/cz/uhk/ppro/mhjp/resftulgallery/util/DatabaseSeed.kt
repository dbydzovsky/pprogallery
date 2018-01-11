package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dao.RoleRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.Role
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.security.PasswordValidator
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Component
@Transactional
class DatabaseSeed(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository,
        private val passwordValidator: PasswordValidator
) {

    @PostConstruct
    fun seedDatabase() {
        createRoleIfNotFound("ROLE_USER")
        createRoleIfNotFound("ROLE_MODERATOR")
        createRoleIfNotFound("ROLE_ADMIN")

        if (userRepository.findAll().isEmpty()) {
            userRepository.save(User(
                    username = "admin",
                    name = "admin",
                    password = passwordValidator.hashPassword("pass", false),
                    roles = roleRepository.findAll().toList()
            ))
        }
    }

    private fun createRoleIfNotFound(name: String) {
        if (roleRepository.getOneByName(name) == null) roleRepository.save(Role(name = name))
    }

}