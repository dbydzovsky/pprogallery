package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.http.ResponseEntity

interface UserService : CrudService<NewUserDto, UpdateUserDto, String> {

    fun changePassword(username: String, updateUserPasswordDto: UpdateUserPasswordDto, authorization: String): ResponseEntity<ResponseDto>

    fun reportUser(username: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto>

    //---------------------Moderator tools--------------------

    fun disableUser(username: String, authorization: String): ResponseEntity<ResponseDto>

    fun enableUser(username: String, authorization: String): ResponseEntity<ResponseDto>

    //----------------------Admin tools-----------------------

    fun getUserRoles(username: String, authorization: String): ResponseEntity<ResponseDto>

    fun updateUserRoles(username: String, authorization: String, roles: List<String>): ResponseEntity<ResponseDto>

}