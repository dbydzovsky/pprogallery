package cz.uhk.ppro.mhjp.resftulgallery.dao

import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ImageRepository : JpaRepository<Image, Long> {

    @Query("SELECT MAX(imageId) from Image")
    fun getMaxId(): Long

    fun getOneByUuid(uuid: String): Image?

}