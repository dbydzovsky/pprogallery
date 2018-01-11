package cz.uhk.ppro.mhjp.resftulgallery.util

import org.springframework.stereotype.Component

@Component
class HashGenerator {

    companion object {
        private const val HASH_LENGTH = 8
        private const val IMAGE_UUID_SALT = "This is image uuid hash"
        private const val IMAGE_DELETEHASH_SALT = "This is image delete hash salt"
    }

}