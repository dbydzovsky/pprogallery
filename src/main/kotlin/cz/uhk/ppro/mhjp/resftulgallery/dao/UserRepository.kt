package cz.uhk.ppro.mhjp.resftulgallery.dao

import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {

    fun <T> getOneByUsername(login: String, type: Class<T>): T?

}