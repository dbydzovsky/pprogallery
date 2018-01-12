package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import org.springframework.http.ResponseEntity

interface CrudService<in NewType, in UpdateType, in IdType> {

    fun createEntity(newType: NewType, authorization: String? = null, parent: String? = null): ResponseEntity<ResponseDto>

    fun readEntity(idType: IdType, authorization: String? = null): ResponseEntity<ResponseDto>

    fun updateEntity(idType: IdType, updateType: UpdateType, authorization: String? = null, authorize: Boolean = true): ResponseEntity<ResponseDto>

    fun deleteEntity(idType: IdType, authorization: String? = null, authorize: Boolean = true): ResponseEntity<ResponseDto>

}