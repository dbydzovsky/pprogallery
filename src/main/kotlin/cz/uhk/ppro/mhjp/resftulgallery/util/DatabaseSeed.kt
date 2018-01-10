package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dao.RoleRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.Role
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Component
@Transactional
class DatabaseSeed(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository
) {

    @PostConstruct
    fun seedDatabase() {
        createRoleIfNotFound("ROLE_USER")
        createRoleIfNotFound("ROLE_MODERATOR")
        createRoleIfNotFound("ROLE_ADMIN")

        if (userRepository.findAll().isEmpty()) {
            userRepository.save(User(
                    username = "admin",
                    password = hashPassword("pass"),
                    roles = roleRepository.findAll().toList()
            ))
        }
    }

    private fun createRoleIfNotFound(name: String) {
        if (roleRepository.getOneByName(name) == null) roleRepository.save(Role(name = name))
    }

}