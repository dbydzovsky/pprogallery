package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewImageDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitReportDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateImageDto
import org.springframework.http.ResponseEntity

interface ImageService : CrudService<NewImageDto, UpdateImageDto, String> {

    fun updateImageWithDeleteHash(uuid: String, updateImage: UpdateImageDto): ResponseEntity<ResponseDto>

    fun deleteImageWithDeleteHash(uuid: String): ResponseEntity<ResponseDto>

    fun reportImage(uuid: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto>

    fun like(uuid: String, authorization: String): ResponseEntity<ResponseDto>

    fun getAll(): ResponseEntity<ResponseDto>

}