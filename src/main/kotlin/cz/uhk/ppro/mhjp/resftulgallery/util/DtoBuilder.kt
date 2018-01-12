package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import cz.uhk.ppro.mhjp.resftulgallery.domain.Role
import cz.uhk.ppro.mhjp.resftulgallery.domain.User
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.stereotype.Component

@Component
class DtoBuilder(
        private val hateoasUtil: HateoasUtil
) {

    // -------------User DTOs-------------

    fun getUserDto(user: User): DataDto
            = hateoasUtil.addSelfObjectLink(
            UserDataDto(
                    username = user.username,
                    name = user.name,
                    dateJoined = user.dateJoined,
                    enabled = user.enabled,
                    private = user.private,
                    images = hateoasUtil.addListItemsLinks(user.images.map { getListImageDto(it) }, "uploaded_image")
            )
    )

    private fun getListUserDto(user: User): DataDto
            = ListUserDataDto(
            username = user.username
    )

    // -------------Image DTOs-------------

    fun getImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            ImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = hateoasUtil.addObjectLink(getListUserDto(image.author!!), "author") as ListUserDataDto,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
            )

    )

    fun getAnonImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            AnonImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
            )

    )

    fun getNewImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            NewImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = hateoasUtil.addObjectLink(getListUserDto(image.author!!), "author") as ListUserDataDto,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
            )
    )

    fun getNewAnonImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            NewAnonImageDataDto(
                    uuid = image.uuid,
                    deleteHash = image.deleteHash,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "likes")
            )
    )

    private fun getListImageDto(image: Image): ListImageDataDto
            = ListImageDataDto(
            uuid = image.uuid
    )

    // -------------Role DTOs-------------

    fun getRolesDto(roles: List<Role>)
            = RolesListDto(
            roles = roles.map { RoleDto(it.name) }
    )

}