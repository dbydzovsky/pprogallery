package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.AddRemoveImagesFromGalleryDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitGalleryDto
import org.springframework.http.ResponseEntity

interface GalleryService : CrudService<SubmitGalleryDto, SubmitGalleryDto, String> {

    fun addImages(galleryUuid: String, toBeAdded: AddRemoveImagesFromGalleryDto, authorization: String): ResponseEntity<ResponseDto>

    fun removeImages(galleryUuid: String, toBeRemoved: AddRemoveImagesFromGalleryDto, authorization: String): ResponseEntity<ResponseDto>

}