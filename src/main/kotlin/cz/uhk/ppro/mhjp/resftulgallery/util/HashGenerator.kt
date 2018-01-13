package cz.uhk.ppro.mhjp.resftulgallery.util

import org.hashids.Hashids
import org.springframework.stereotype.Component

@Component
class HashGenerator {

    companion object {
        private const val IMAGE_UUID_SALT = "This is uuid salt"
        private const val IMAGE_UUID_LENGTH = 8
        private const val DELETEHASH_SALT = "This is delete hash salt"
        private const val DELETE_HASH_LENGTH = 16
        private const val COMMENT_UUID_SALT = "This is comment salt"
        private const val COMMENT_UUID_LENGTH = 8
        private const val GALLERY_UUID_SALT = "This is gallery salt"
        private const val GALLERY_UUID_LENGTH = 8
    }

    val imageUuidGenerator = Hashids(IMAGE_UUID_SALT, IMAGE_UUID_LENGTH)
    val deleteHashGenerator = Hashids(DELETEHASH_SALT, DELETE_HASH_LENGTH)
    val commentUuidGenerator = Hashids(COMMENT_UUID_SALT, COMMENT_UUID_LENGTH)
    val galleryUuidGenerator = Hashids(GALLERY_UUID_SALT, GALLERY_UUID_LENGTH)

    fun hashImageIdToUuid(id: Long) = imageUuidGenerator.encode(id)

    fun hashIdToDeleteHash(id: Long) = deleteHashGenerator.encode(id)

    fun hashCommentIdToUuid(id: Long) = commentUuidGenerator.encode(id)

    fun hashGalleryIdToUuid(id: Long) = galleryUuidGenerator.encode(id)



}