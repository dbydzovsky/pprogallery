package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import cz.uhk.ppro.mhjp.resftulgallery.domain.ImageComment
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
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "user_like"),
                    comments = hateoasUtil.addListItemsLinks(image.comments.map { getListCommentDto(it) }, "comment")
            )

    )

    fun getAnonImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            AnonImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    likedByUsers = hateoasUtil.addListItemsLinks(image.likedByUsers.map { getListUserDto(it) }, "user_like"),
                    comments = hateoasUtil.addListItemsLinks(image.comments.map { getListCommentDto(it) }, "comment")
            )

    )

    fun getNewImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            NewImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = hateoasUtil.addObjectLink(getListUserDto(image.author!!), "author") as ListUserDataDto
            )
    )

    fun getNewAnonImageDto(image: Image): DataDto
            = hateoasUtil.addSelfObjectLink(
            NewAnonImageDataDto(
                    uuid = image.uuid,
                    deleteHash = image.deleteHash,
                    description = image.description,
                    imageBytes = image.imageBytes
            )
    )

    private fun getListImageDto(image: Image): ListImageDataDto
            = ListImageDataDto(
            uuid = image.uuid
    )

    // -------------Comment DTOs-------------

    fun getCommentDto(comment: ImageComment)
            = hateoasUtil.addSelfObjectLink(
            CommentDataDto(
                    uuid = comment.uuid,
                    author = hateoasUtil.addObjectLink(ListUserDataDto(comment.author.name), "author") as ListUserDataDto,
                    image = hateoasUtil.addObjectLink(ListImageDataDto(comment.image.uuid), "commented_image") as ListImageDataDto,
                    content = comment.content
            )
    )

    private fun getListCommentDto(comment: ImageComment): ListCommentDataDto
            = ListCommentDataDto(
            uuid = comment.uuid
    )

    // -------------Role DTOs-------------

    fun getRolesDto(roles: List<Role>)
            = RolesListDto(
            roles = roles.map { it.name }
    )

}