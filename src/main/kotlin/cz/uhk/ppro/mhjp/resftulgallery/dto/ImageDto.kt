package cz.uhk.ppro.mhjp.resftulgallery.dto

class NewImageDto(
        val description: String  = "",
        val imageBytes: ByteArray = byteArrayOf(),
        val private: Boolean = false
)

class UpdateImageDto(
        val description: String = "",
        val private: Boolean = false
)

open class NewImageDataDto(
        val uuid: String,
        val description: String,
        val deleteHash: String,
        val imageBytes: ByteArray,
        val private: Boolean,
        val anonymous: Boolean,
        val author: ListUserDataDto? = null,
        val likedByUsers: List<DataDto>
) : DataDto()

open class ImageDataDto(
        val uuid: String,
        val description: String,
        val imageBytes: ByteArray,
        val private: Boolean,
        val anonymous: Boolean,
        val author: ListUserDataDto? = null,
        val likedByUsers: List<DataDto>
) : DataDto()

open class ListImageDataDto(
        val uuid: String
) : DataDto()