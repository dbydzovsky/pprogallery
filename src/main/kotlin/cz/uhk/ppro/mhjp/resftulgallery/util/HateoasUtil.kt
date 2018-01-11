package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.ListUserDataDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.DataDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UserDataDto
import cz.uhk.ppro.mhjp.resftulgallery.rest.UserController
import org.springframework.hateoas.mvc.ControllerLinkBuilder.*
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

    fun addListItemsLinks(dtos: List<DataDto>, rel: String) {
        for (dto in dtos) {
            when (dto) {
                is ListUserDataDto -> dto.add(linkTo(UserController::class.java)
                        .slash("u")
                        .slash(dto.username)
                        .withRel(rel))
            }
        }
    }
}