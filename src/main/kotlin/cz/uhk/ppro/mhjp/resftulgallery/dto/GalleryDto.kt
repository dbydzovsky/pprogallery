package cz.uhk.ppro.mhjp.resftulgallery.dto

class SubmitGalleryDto(
        val title: String = "",
        val private: Boolean = false,
        val images: List<String> = emptyList()
)

class AddRemoveImagesFromGalleryDto(
        val images: List<String> = emptyList()
)

open class GalleryDataDto(
        val uuid: String,
        val title: String,
        val private: Boolean,
        val author: ListUserDataDto,
        val images: List<DataDto>
) : DataDto()

open class ListGalleryDataDto(
        val uuid: String
) : DataDto()

