package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dao.ImageRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import cz.uhk.ppro.mhjp.resftulgallery.security.AuthorizationManager
import cz.uhk.ppro.mhjp.resftulgallery.security.JwtHandler
import cz.uhk.ppro.mhjp.resftulgallery.util.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl(
        private val dtoValidator: DtoValidator,
        private val imageRepository: ImageRepository,
        private val hashGenerator: HashGenerator,
        private val jwtHandler: JwtHandler,
        private val userRepository: UserRepository,
        private val authorizationManager: AuthorizationManager,
        private val dtoBuilder: DtoBuilder,
        private val responseBuilder: ResponseBuilder
) : ImageService {

    override fun createEntity(newType: NewImageDto, authorization: String?): ResponseEntity<ResponseDto> {
        dtoValidator.validateDto(newType)
        val maxId = imageRepository.getMaxId() + 1
        val image = if (authorization == null) {
            Image(
                    uuid = hashGenerator.hashIdToUuid(maxId),
                    description = newType.description,
                    deleteHash = hashGenerator.hashIdToDeleteHash(maxId),
                    imageBytes = newType.imageBytes,
                    anonymous = true,
                    private = newType.private
            )
        } else {
            val author = userRepository.getOneByUsername(jwtHandler.validateToken(authorization)) ?:
                    throw TokenOwnerNotFoundException("Error while uploading image. User not found.")
            Image(
                    uuid = hashGenerator.hashIdToUuid(maxId),
                    description = newType.description,
                    deleteHash = hashGenerator.hashIdToDeleteHash(maxId),
                    imageBytes = newType.imageBytes,
                    dateUploaded = System.currentTimeMillis(),
                    author = author,
                    private = newType.private
            )
        }
        imageRepository.save(image)
        val dto = dtoBuilder.getNewImageDto(image)
        return responseBuilder.buildSuccessfulResponse(200, "Image successfully uploaded", dto)
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        val image = imageRepository.getOneByUuid(idType) ?:
                throw ContentNotFoundException("Error while retrieving image. Image not found.")
        if (image.private) authorizationManager.authorize(authorization, idType)
        val dto = dtoBuilder.getImageDto(image)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully retrieved image.", dto)
    }

    override fun updateEntity(idType: String, updateType: UpdateImageDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        if (authorize) authorizationManager.authorize(authorization, idType)
        val editingImage = imageRepository.getOneByUuid(idType) ?:
                throw ContentNotFoundException("Error while editing image description. Image not found.")
        dtoValidator.validateDto(updateType)
        val editedImage = editingImage.copy(
                description = updateType.description,
                private = editingImage.private
        )
        imageRepository.save(editedImage)
        val dto = dtoBuilder.getImageDto(editedImage)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully edited image.", dto)
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        if (authorize) authorizationManager.authorize(authorization, idType)
        val image = imageRepository.getOneByUuid(idType) ?:
                throw ContentNotFoundException("Error while deleting image. Image not found.")
        imageRepository.delete(image)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully deleted image.")
    }

    override fun updateImageWithDeleteHash(uuid: String, updateImage: UpdateImageDto): ResponseEntity<ResponseDto> {
        return updateEntity(uuid, updateImage)
    }

    override fun deleteImageWithDeleteHash(uuid: String): ResponseEntity<ResponseDto> {
        return deleteEntity(uuid)
    }

    override fun reportImage(uuid: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun like(uuid: String, authorization: String): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): ResponseEntity<ResponseDto> {
        val images = imageRepository.findAll()
        val dtos = dtoBuilder.getImagesDtos(images, "image")
        return responseBuilder.buildSuccessfulResponse(200, "asdf", *dtos.toTypedArray())
    }
}