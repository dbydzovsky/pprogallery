package cz.uhk.ppro.mhjp.resftulgallery.util

import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.stereotype.Component

@Component
class DtoValidator {

    companion object {
        private const val MAX_IMG_SIZE = 10000000
    }

    fun validateDto(dto: Any) {

        when (dto) {
            is NewUserDto -> validateNewUser(dto)
            is UpdateUserDto -> validateUpdateUser(dto)
            is UpdateUserPasswordDto -> validateUpdatePassword(dto)
            is SubmitReportDto -> validateReport(dto)
            is NewImageDto -> validateNewImage(dto)
            is SubmitCommentDto -> validateComment(dto)
            else -> throw UnknownDtoException("Error while processing DTO. Unknown DTO.")
        }

    }

    private fun validateNewUser(dto: NewUserDto) {
        if (dto.username == "" || dto.password == "" || dto.repeatPassword == "")
            throw IncompleteSubmittedDtoException("Error while creating new user. You have not specified all required parameters.")
    }

    private fun validateUpdateUser(dto: UpdateUserDto) {
        if (dto.username == "" || dto.name == "")
            throw IncompleteSubmittedDtoException("Error while updating user. You have not specified all required parameters.")
    }

    private fun validateUpdatePassword(dto: UpdateUserPasswordDto) {
        if (dto.oldPassword == "" || dto.newPassword == "" || dto.repeatNewPassword == "")
            throw IncompleteSubmittedDtoException("Error while updating password. You have not specified all required parameters.")
    }

    private fun validateReport(dto: SubmitReportDto) {
        if (dto.author == "" || dto.reason == "")
            throw IncompleteSubmittedDtoException("Error while submitting report. You have not specified all required parameters.")
    }

    private fun validateNewImage(dto: NewImageDto) {
        if (dto.imageBytes.isEmpty()) {
            throw IncompleteSubmittedDtoException("Error while uploading image. Image has no content.")
        }
        if (dto.imageBytes.size > MAX_IMG_SIZE)
            throw ImageSizeTooLargeException("Error while uploading image. Image is bigger than 10MB.")
    }

    private fun validateComment(dto: SubmitCommentDto) {
        if (dto.content == "")
            throw IncompleteSubmittedDtoException("Error while submitting comment. You have not specified all required parameters.")
    }
}