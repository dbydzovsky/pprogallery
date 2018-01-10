package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import org.springframework.http.ResponseEntity

interface CrudService<in NewType, in UpdateType, in IdType> {

    fun createEntity(newType: NewType): ResponseEntity<ResponseDto>

    fun readEntity(idType: IdType, authorization: String? = null): ResponseEntity<ResponseDto>

    fun updateEntity(idType: IdType, updateType: UpdateType, authorization: String, authorize: Boolean = true): ResponseEntity<ResponseDto>

    fun deleteEntity(idType: IdType, authorization: String, authorize: Boolean = true): ResponseEntity<ResponseDto>

}