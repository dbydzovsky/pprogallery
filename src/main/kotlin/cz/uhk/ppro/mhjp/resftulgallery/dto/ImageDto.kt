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
        val imageBytes: ByteArray,
        val private: Boolean,
        val author: ListUserDataDto
) : DataDto()

open class NewAnonImageDataDto(
        val uuid: String,
        val deleteHash: String,
        val description: String,
        val imageBytes: ByteArray
) : DataDto()

open class ImageDataDto(
        val uuid: String,
        val description: String,
        val imageBytes: ByteArray,
        val private: Boolean,
        val author: ListUserDataDto,
        val likedByUsers: List<DataDto>,
        val comments: List<DataDto>
) : DataDto()

open class AnonImageDataDto(
        val uuid: String,
        val description: String,
        val imageBytes: ByteArray,
        val likedByUsers: List<DataDto>,
        val comments: List<DataDto>
) : DataDto()

open class ListImageDataDto(
        val uuid: String
) : DataDto()

class ImagesListDto(
        val images: List<ListImageDataDto>
) : DataDtoWithoutLinks()