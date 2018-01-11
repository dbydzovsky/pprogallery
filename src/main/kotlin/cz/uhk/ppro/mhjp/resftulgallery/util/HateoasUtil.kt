package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import cz.uhk.ppro.mhjp.resftulgallery.rest.ImageController
import cz.uhk.ppro.mhjp.resftulgallery.rest.UserController
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class HateoasUtil {

    fun addObjectLink(dto: DataDto, rel: String): DataDto {
        when (dto) {
            is UserDataDto -> dto.add(linkTo(UserController::class.java)
                    .slash("u")
                    .slash(dto.username)
                    .withRel(rel)
            )
            is ListUserDataDto -> dto.add(linkTo(UserController::class.java)
                    .slash("u")
                    .slash(dto.username)
                    .withRel(rel)
            )
            is NewImageDataDto -> dto.add(linkTo(ImageController::class.java)
                    .slash("i")
                    .slash(dto.uuid)
                    .withRel(rel)
            )
            is ImageDataDto -> dto.add(linkTo(ImageController::class.java)
                    .slash("i")
                    .slash(dto.uuid)
                    .withRel(rel)
            )
            is ListImageDataDto -> dto.add(linkTo(ImageController::class.java)
                    .slash("i")
                    .slash(dto.uuid)
                    .withRel(rel)
            )
            else -> {
                throw UnknownDtoException("Unsupported response format!")
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