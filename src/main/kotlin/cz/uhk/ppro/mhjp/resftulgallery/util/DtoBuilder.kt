package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.stereotype.Component

@Component
class DtoBuilder(
        private val hateoasUtil: HateoasUtil
) {

    fun getUserDto(user: User): DataDto
            = hateoasUtil.addSelfObjectLink(
            UserDataDto(
                    username = user.username,
                    name = user.name,
                    dateJoined = user.dateJoined,
                    enabled = user.enabled,
                    private = user.private
            )
    )

    private fun getListUserDto(user: User): DataDto
            = ListUserDataDto(
            username = user.username
    )

    fun getImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            ImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    anonymous = image.anonymous,
                    author = if (image.author != null) {
                        hateoasUtil.addObjectLink(
                                getListUserDto(
                                        image.author
                                ),
                                "author"
                        ) as ListUserDataDto
                    } else {
                        null
                    },
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
            )

    )

    fun getNewImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            NewImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    deleteHash = image.deleteHash,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    anonymous = image.anonymous,
                    author = if (image.author != null) {
                        hateoasUtil.addObjectLink(
                                getListUserDto(
                                        image.author
                                ),
                                "author"
                        ) as ListUserDataDto
                    } else {
                        null
                    },
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
            )
    )

    fun getImagesDtos(images: List<Image>, rel: String)
            = hateoasUtil.addListItemsLinks(images.map { getListImageDto(it) }, rel)

    private fun getListImageDto(image: Image): ListImageDataDto
            = ListImageDataDto(
            uuid = image.uuid
    )

}