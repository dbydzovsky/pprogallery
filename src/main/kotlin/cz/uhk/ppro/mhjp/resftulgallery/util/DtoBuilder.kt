package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.domain.*
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.stereotype.Component

@Component
class DtoBuilder(
        private val hateoasUtil: HateoasUtil
) {

    // -------------User DTOs-------------

    fun getUserDto(user: User) = hateoasUtil.addSelfObjectLink(
            UserDataDto(
                    username = user.username,
                    name = user.name,
                    dateJoined = user.dateJoined,
                    enabled = user.enabled,
                    private = user.private,
                    images = hateoasUtil.addListItemsLinks(user.images.map { getListImageDto(it) }, "uploaded_image")
            )
    )

    private fun getListUserDto(user: User) = ListUserDataDto(username = user.username)

    // -------------Image DTOs-------------

    fun getImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            ImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = hateoasUtil.addObjectLink(getListUserDto(image.author!!), "author") as ListUserDataDto,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "user_like"),
                    comments = hateoasUtil.addListItemsLinks(image.comments.map { getListCommentDto(it) }, "comment")
            )

    )

    fun getAnonImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            AnonImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "user_like"),
                    comments = hateoasUtil.addListItemsLinks(image.comments.map { getListCommentDto(it) }, "comment")
            )

    )

    fun getNewImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            NewImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = hateoasUtil.addObjectLink(getListUserDto(image.author!!), "author") as ListUserDataDto
            )
    )

    fun getNewAnonImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            NewAnonImageDataDto(
                    uuid = image.uuid,
                    deleteHash = image.deleteHash,
                    description = image.description,
                    imageBytes = image.imageBytes
            )
    )

    private fun getListImageDto(image: Image) = ListImageDataDto(uuid = image.uuid)

    // -------------Comment DTOs-------------

    fun getCommentDto(comment: ImageComment) = hateoasUtil.addSelfObjectLink(
            CommentDataDto(
                    uuid = comment.uuid,
                    author = hateoasUtil.addObjectLink(ListUserDataDto(comment.author.name), "author") as ListUserDataDto,
                    image = hateoasUtil.addObjectLink(ListImageDataDto(comment.image.uuid), "commented_image") as ListImageDataDto,
                    content = comment.content
            )
    )

    private fun getListCommentDto(comment: ImageComment) = ListCommentDataDto(uuid = comment.uuid)

    // -------------Gallery DTOs------------

    fun getGalleryDto(gallery: Gallery) = hateoasUtil.addSelfObjectLink(
            GalleryDataDto(
                    uuid = gallery.uuid,
                    title = gallery.title,
                    private = gallery.private,
                    images = hateoasUtil.addListItemsLinks(gallery.images.map { getListImageDto(it) }, "image"),
                    author = hateoasUtil.addObjectLink(getListUserDto(gallery.author), "author") as ListUserDataDto
            )
    )

    private fun getListGalleryDto(gallery: Gallery) = ListGalleryDataDto(uuid = gallery.uuid)

    // -------------Role DTOs-------------

    fun getRolesDto(roles: List<Role>) = RolesListDto(roles = roles.map { it.name })

}