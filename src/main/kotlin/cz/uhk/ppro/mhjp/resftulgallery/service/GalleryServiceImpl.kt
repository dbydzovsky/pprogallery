package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dao.GalleryRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.ImageRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.Gallery
import cz.uhk.ppro.mhjp.resftulgallery.dto.AddRemoveImagesFromGalleryDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitGalleryDto
import cz.uhk.ppro.mhjp.resftulgallery.security.AuthorizationManager
import cz.uhk.ppro.mhjp.resftulgallery.util.ContentNotFoundException
import cz.uhk.ppro.mhjp.resftulgallery.util.DtoBuilder
import cz.uhk.ppro.mhjp.resftulgallery.util.HashGenerator
import cz.uhk.ppro.mhjp.resftulgallery.util.ResponseBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class GalleryServiceImpl(
        private val galleryRepository: GalleryRepository,
        private val imageRepository: ImageRepository,
        private val hashGenerator: HashGenerator,
        private val authorizationManager: AuthorizationManager,
        private val dtoBuilder: DtoBuilder,
        private val responseBuilder: ResponseBuilder
) : GalleryService {

    override fun createEntity(newType: SubmitGalleryDto, authorization: String?, parent: String?): ResponseEntity<ResponseDto> {
        val author = authorizationManager.authorize(token = authorization, specifiedRoles = listOf("ROLE_USER", "ROLE_MODERATOR"))
        val gallery = Gallery(
                uuid = hashGenerator.hashGalleryIdToUuid(System.currentTimeMillis()),
                author = author,
                private = newType.private,
                images = newType.images.mapNotNull { imageRepository.getOneByUuid(it) },
                title = newType.title
        )
        galleryRepository.save(gallery)
        val dto = dtoBuilder.getGalleryDto(gallery)
        return responseBuilder.buildSuccessfulResponse(200, "Gallery successfully created.", dto)
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        val gallery = galleryRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while retrieving gallery. Gallery not found.")
        if (gallery.private) authorizationManager.authorize(authorization, gallery.author.name, listOf("ROLE_MODERATOR"))
        val dto = dtoBuilder.getGalleryDto(gallery)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully retrieved gallery.", dto)
    }

    override fun updateEntity(idType: String, updateType: SubmitGalleryDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        val galleryToBeEdited = galleryRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while editing gallery. Gallery not found.")
        authorizationManager.authorize(authorization, galleryToBeEdited.author.name)
        val editedGallery = galleryToBeEdited.copy(
                title = updateType.title,
                private = updateType.private,
                images = updateType.images.mapNotNull { imageRepository.getOneByUuid(it) }
        )
        galleryRepository.save(editedGallery)
        val dto = dtoBuilder.getGalleryDto(editedGallery)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully edited gallery.", dto)
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        val gallery = galleryRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while deleting gallery. Gallery not found.")
        authorizationManager.authorize(authorization, gallery.author.name)
        galleryRepository.delete(gallery)
        return responseBuilder.buildSuccessfulResponse(204, "Gallery successfully removed.")
    }

    override fun addImages(galleryUuid: String, toBeAdded: AddRemoveImagesFromGalleryDto, authorization: String): ResponseEntity<ResponseDto> {
        val gallery = galleryRepository.getOneByUuid(galleryUuid)
                ?: throw ContentNotFoundException("Error while adding image to gallery. Gallery not found.")
        authorizationManager.authorize(authorization, gallery.author.name)
        val add = toBeAdded.images.mapNotNull { imageRepository.getOneByUuid(it) }
        val newGallery = gallery.copy(
                images = gallery.images.plus(add)
        )
        galleryRepository.save(newGallery)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully added image to gallery")
    }

    override fun removeImages(galleryUuid: String, toBeRemoved: AddRemoveImagesFromGalleryDto, authorization: String): ResponseEntity<ResponseDto> {
        val gallery = galleryRepository.getOneByUuid(galleryUuid)
                ?: throw ContentNotFoundException("Error while removing image from gallery. Gallery not found.")
        authorizationManager.authorize(authorization, gallery.author.name)
        val remove = toBeRemoved.images.mapNotNull { imageRepository.getOneByUuid(it) }
        val newGallery = gallery.copy(
                images = gallery.images.minus(remove)
        )
        galleryRepository.save(newGallery)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully removed images from gallery")
    }
}