package cz.uhk.ppro.mhjp.resftulgallery.dao

import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Long> {

    @Query("SELECT COALESCE(MAX(id), 0) from Image")
    fun getMaxId(): Long

    fun getOneByUuid(uuid: String): Image?

}