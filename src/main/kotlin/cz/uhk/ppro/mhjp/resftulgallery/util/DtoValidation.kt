package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewUserDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitReportDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateUserDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateUserPasswordDto

fun validateDto(dto: Any) {

    when (dto) {
        is NewUserDto -> validateNewUser(dto)
        is UpdateUserDto -> validateUpdateUser(dto)
        is UpdateUserPasswordDto -> validateUpdatePassword(dto)
        is SubmitReportDto -> validateReport(dto)
        else -> throw UnknownDtoException("Error while processing DTO. Unknown DTO.")
    }

}

private fun validateNewUser(dto: NewUserDto) {
    if (dto.username == "" || dto.password == "" || dto.repeatPassword == "")
        throw IncompleteSubmitedDtoException("Error while creating new user. You have not specified all required parameters.")
}

private fun validateUpdateUser(dto: UpdateUserDto) {
    if (dto.username == "" || dto.name == "")
        throw IncompleteSubmitedDtoException("Error while updating user. You have not specified all required parameters.")
}

private fun validateUpdatePassword(dto: UpdateUserPasswordDto) {
    if (dto.oldPassword == "" || dto.newPassword == "" || dto.repeatNewPassword == "")
        throw IncompleteSubmitedDtoException("Error while updating password. You have not specified all required parameters.")
}

private fun validateReport(dto: SubmitReportDto) {
    if (dto.author == "" || dto.reason == "") {

    }
}