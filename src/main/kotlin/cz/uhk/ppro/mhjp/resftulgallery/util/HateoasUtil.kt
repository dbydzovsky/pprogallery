package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.DataDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UserDataDto
import cz.uhk.ppro.mhjp.resftulgallery.rest.UserController
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class HateoasUtil {
    fun addSelfObjectLink(dto: DataDto): DataDto {
        when (dto) {
            is UserDataDto -> dto.add(linkTo(UserController::class.java)
                    .slash("u")
                    .slash(dto.username)
                    .withSelfRel()
            )
            else -> {
                throw UnknownDtoException("Unsupported response format!")
            }
        }
        return dto
    }

    fun addObjectLink(dto: DataDto, rel: String): DataDto {
        when (dto) {
            is UserDataDto -> dto.add(linkTo(UserController::class.java)
                    .slash("u")
                    .slash(dto.username)
                    .withSelfRel()
            )
            else -> {
                throw UnknownDtoException("Unsupported response format!")
            }
        }
        return dto
    }

    fun addListItemsLinks(dtos: List<DataDto>, rel: String): List<DataDto> {
        return dtos.onEach { addObjectLink(it, rel) }
    }
}