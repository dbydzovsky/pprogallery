package cz.uhk.ppro.mhjp.resftulgallery.dao

import cz.uhk.ppro.mhjp.resftulgallery.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Int> {

    fun getOneById(id: Int): Role?

    fun getOneByName(name: String): Role?

    fun findByName(name: String): List<Role>

}