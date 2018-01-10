package cz.uhk.ppro.mhjp.resftulgallery.dto

class NewUserDto(
        val username: String,
        val password: String,
        val repeatPassword: String
)

class UpdateUserDto(
        val username: String,
        val private: Boolean
)

class UpdateUserPasswordDto(
        val oldPassword: String,
        val newPassword: String,
        val repeatNewPassword: String
)

open class UserDataDto(
        val username: String,
        val dateJoined: Long,
        val enabled: Boolean
) : DataDto()

open class ListUserDataDto(
        val username: String
) : DataDto()