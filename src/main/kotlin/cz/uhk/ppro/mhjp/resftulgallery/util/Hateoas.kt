package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.ListUserDataDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.DataDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UserDataDto
import cz.uhk.ppro.mhjp.resftulgallery.rest.UserController
import org.springframework.hateoas.mvc.ControllerLinkBuilder.*

fun addSelfObjectLink(dto: DataDto) = when (dto) {
    is UserDataDto -> dto.add(linkTo(UserController::class.java)
            .slash("u")
            .slash(dto.username)
            .withSelfRel()
    )
    else -> {
        throw UnknownDtoException("Unsupported response format!")
    }
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