package cz.uhk.ppro.mhjp.resftulgallery.dto

class NewImageDto(
        val description: String  = "",
        val imageBytes: ByteArray = byteArrayOf()
)

class UpdateImageDto(
        val description: String = ""
)

open class NewImageDataDto(
        val uuid: String,
        val description: String,
        val deleteHash: String,
        val imageBytes: ByteArray,
        val author: ListUserDataDto
) : DataDto()

open class ImageDataDto(
        val uuid: String,
        val description: String,
        val imageBytes: ByteArray,
        val author: ListUserDataDto,
        val likedByUsers: List<ListUserDataDto>
) : DataDto()

open class ListImageDataDto(
        val uuid: String
) : DataDto()