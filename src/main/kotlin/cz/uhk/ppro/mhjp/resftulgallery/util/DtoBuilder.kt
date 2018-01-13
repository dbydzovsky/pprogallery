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
                    images = user.images.map { getListImageDto(it, "uploaded_image") }
            )
    )

    fun getListUserDto(user: User, rel: String) = hateoasUtil.addObjectLink(ListUserDataDto(username = user.username), rel)

    // -------------Image DTOs-------------

    fun getImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            ImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = getListUserDto(image.author!!, "author") as ListUserDataDto,
                    likedByUsers = image.likedByUsers.map { getListUserDto(it, "likes") },
                    comments = image.comments.map { getListCommentDto(it, "comment") }
            )

    )

    fun getAnonImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            AnonImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    likedByUsers = image.likedByUsers.map { getListUserDto(it, "likes") },
                    comments = image.comments.map { getListCommentDto(it, "comment") }
            )

    )

    fun getNewImageDto(image: Image) = hateoasUtil.addSelfObjectLink(
            NewImageDataDto(
                    uuid = image.uuid,
                    description = image.description,
                    imageBytes = image.imageBytes,
                    private = image.private,
                    author = getListUserDto(image.author!!, "author") as ListUserDataDto
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

    fun getListImageDto(image: Image, rel: String) = hateoasUtil.addObjectLink(ListImageDataDto(uuid = image.uuid), rel)

    // -------------Comment DTOs-------------

    fun getCommentDto(comment: ImageComment) = hateoasUtil.addSelfObjectLink(
            CommentDataDto(
                    uuid = comment.uuid,
                    author = getListUserDto(comment.author, "author") as ListUserDataDto,
                    image = getListImageDto(comment.image, "is_comment_of") as ListImageDataDto,
                    content = comment.content
            )
    )

    fun getListCommentDto(comment: ImageComment, rel: String) = hateoasUtil.addObjectLink(ListCommentDataDto(uuid = comment.uuid), rel)

    // -------------Gallery DTOs------------

    fun getGalleryDto(gallery: Gallery) = hateoasUtil.addSelfObjectLink(
            GalleryDataDto(
                    uuid = gallery.uuid,
                    title = gallery.title,
                    private = gallery.private,
                    images = gallery.images.map { getListImageDto(it, "gallery_image") },
                    author = getListUserDto(gallery.author, "author") as ListUserDataDto
            )
    )

    fun getListGalleryDto(gallery: Gallery, rel:String) = hateoasUtil.addObjectLink(ListGalleryDataDto(uuid = gallery.uuid), rel)

    // -------------Role DTOs-------------

    fun getRolesDto(roles: List<Role>) = RolesListDto(roles = roles.map { it.name })

}