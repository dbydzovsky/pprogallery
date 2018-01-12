package cz.uhk.ppro.mhjp.resftulgallery.dto

class RoleDto(
        val name: String
) : DataDtoWithoutLinks()

class RolesListDto(
        val roles: List<RoleDto>
) : DataDtoWithoutLinks()