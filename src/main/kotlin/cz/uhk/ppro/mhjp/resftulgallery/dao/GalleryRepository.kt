package cz.uhk.ppro.mhjp.resftulgallery.dao

import cz.uhk.ppro.mhjp.resftulgallery.domain.Gallery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GalleryRepository : JpaRepository<Gallery, String> {

    fun getOneByUuid(uuid: String): Gallery?

}