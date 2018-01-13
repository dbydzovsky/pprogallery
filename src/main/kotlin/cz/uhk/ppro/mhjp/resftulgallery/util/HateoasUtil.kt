package cz.uhk.ppro.mhjp.resftulgallery.util

import com.nhaarman.mockito_kotlin.any
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import cz.uhk.ppro.mhjp.resftulgallery.rest.GalleryController
import cz.uhk.ppro.mhjp.resftulgallery.rest.ImageCommentController
import cz.uhk.ppro.mhjp.resftulgallery.rest.ImageController
import cz.uhk.ppro.mhjp.resftulgallery.rest.UserController
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class HateoasUtil {

    fun addObjectLink(dto: DataDto, rel: String): DataDto {
        when (dto) {
            is UserDataDto -> dto.add(linkTo(methodOn(UserController::class.java)
                    .retrieveUser(dto.username, any()))
                    .withRel(rel)
            )
            is ListUserDataDto -> dto.add(linkTo(methodOn(UserController::class.java)
                    .retrieveUser(dto.username, any()))
                    .withRel(rel)
            )
            is NewImageDataDto -> dto.add(linkTo(methodOn(ImageController::class.java)
                    .retrieveImage(dto.uuid, any()))
                    .withRel(rel)
                )
            is NewAnonImageDataDto -> {
                dto.add(linkTo(methodOn(ImageController::class.java)
                        .retrieveImage(dto.uuid, any()))
                        .withRel(rel)
                )
                dto.add(linkTo(methodOn(ImageController::class.java)
                        .deleteImageWithDeleteHash(dto.uuid, dto.deleteHash))
                        .withRel("delete_hash"))
            }
            is ImageDataDto -> dto.add(linkTo(methodOn(ImageController::class.java)
                    .retrieveImage(dto.uuid, any()))
                    .withRel(rel)
            )
            is AnonImageDataDto -> dto.add(linkTo(methodOn(ImageController::class.java)
                    .retrieveImage(dto.uuid, any()))
                    .withRel(rel)
            )
            is ListImageDataDto -> dto.add(linkTo(methodOn(ImageController::class.java)
                    .retrieveImage(dto.uuid, any()))
                    .withRel(rel)
            )
            is CommentDataDto -> dto.add(linkTo(methodOn(ImageCommentController::class.java)
                    .getComment(dto.uuid, any()))
                    .withRel(rel)
            )
            is ListCommentDataDto -> dto.add(linkTo(methodOn(ImageCommentController::class.java)
                    .getComment(dto.uuid, any()))
                    .withRel(rel)
            )
            is GalleryDataDto -> dto.add(linkTo(methodOn(GalleryController::class.java)
                    .retrieveGallery(dto.uuid, any()))
                    .withRel(rel)
            )
            is ListGalleryDataDto -> dto.add(linkTo(methodOn(GalleryController::class.java)
                    .retrieveGallery(dto.uuid, any()))
                    .withRel(rel)
            )
            else -> {
                throw UnknownDtoException("Unsupported response format.")
            }
        }
        return dto
    }

    fun addSelfObjectLink(dto: DataDto): DataDto {
        return addObjectLink(dto, "self")
    }

    fun addListItemsLinks(dtos: List<DataDto>, rel: String): List<DataDto> {
        return dtos.onEach { addObjectLink(it, rel) }
    }
}