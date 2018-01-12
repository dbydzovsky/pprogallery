package cz.uhk.ppro.mhjp.resftulgallery.dao

import cz.uhk.ppro.mhjp.resftulgallery.domain.ImageComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageCommentRepository : JpaRepository<ImageComment, String> {

    fun getOneByUuid(uuid: String): ImageComment?

}