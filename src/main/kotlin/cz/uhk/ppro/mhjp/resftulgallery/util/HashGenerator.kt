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
    }

    val uuidGenerator = Hashids(IMAGE_UUID_SALT, IMAGE_UUID_LENGTH)
    val deleteHashGenerator = Hashids(DELETEHASH_SALT, DELETE_HASH_LENGTH)

    fun hashIdToUuid(id: Long) = uuidGenerator.encode(id)

    fun hashIdToDeleteHash(id: Long) = deleteHashGenerator.encode(id)



}