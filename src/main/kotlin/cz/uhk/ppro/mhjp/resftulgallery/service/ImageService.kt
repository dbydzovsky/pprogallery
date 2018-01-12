package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import org.springframework.http.ResponseEntity

interface ImageService : CrudService<NewImageDto, UpdateImageDto, String> {

    fun updateImageWithDeleteHash(uuid: String, deleteHash: String, updateImage: UpdateImageDto): ResponseEntity<ResponseDto>

    fun deleteImageWithDeleteHash(uuid: String, deleteHash: String): ResponseEntity<ResponseDto>

    fun reportImage(uuid: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto>

    fun like(uuid: String, authorization: String): ResponseEntity<ResponseDto>

}