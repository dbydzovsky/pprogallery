package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.stereotype.Component

@Component
class DtoBuilder(
        private val hateoasUtil: HateoasUtil
) {

    fun getUserDto(user: User): UserDataDto
            = UserDataDto(
            username = user.username,
            name = user.name,
            dateJoined = user.dateJoined,
            enabled = user.enabled,
            private = user.private
    )

    fun getListUserDto(user: User): ListUserDataDto
            = ListUserDataDto(
            username = user.username
    )

    fun getImageDto(image: Image): ImageDataDto
            = ImageDataDto(
            uuid = image.uuid,
            description = image.description,
            imageBytes = image.imageBytes,
            private = image.private,
            anonymous = image.anonymous,
            author = hateoasUtil.addObjectLink(
                    getListUserDto(
                            image.author!!
                    ),
                    "author"
            ) as ListUserDataDto,
            likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")

    )

    fun getNewImageDto(image: Image): NewImageDataDto
            = NewImageDataDto(
            uuid = image.uuid,
            description = image.description,
            deleteHash = image.deleteHash,
            imageBytes = image.imageBytes,
            private = image.private,
            anonymous = image.anonymous,
            author = hateoasUtil.addObjectLink(
                    getListUserDto(
                            image.author!!
                    ),
                    "author"
            ) as ListUserDataDto,
            likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
    )

    fun getListImageDto(image: Image): ListImageDataDto
            = ListImageDataDto(
            uuid = image.uuid
    )

}