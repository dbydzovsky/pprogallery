package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dao.ImageRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.UserRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.Image
import cz.uhk.ppro.mhjp.resftulgallery.dto.*
import cz.uhk.ppro.mhjp.resftulgallery.security.AuthorizationManager
import cz.uhk.ppro.mhjp.resftulgallery.util.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl(
        private val dtoValidator: DtoValidator,
        private val imageRepository: ImageRepository,
        private val hashGenerator: HashGenerator,
        private val userRepository: UserRepository,
        private val authorizationManager: AuthorizationManager,
        private val dtoBuilder: DtoBuilder,
        private val responseBuilder: ResponseBuilder
) : ImageService {

    override fun createEntity(newType: NewImageDto, authorization: String?, parent: String?): ResponseEntity<ResponseDto> {
        dtoValidator.validateDto(newType)
        val image = if (authorization == null) {
            Image(
                    uuid = hashGenerator.hashImageIdToUuid(System.currentTimeMillis()),
                    deleteHash = hashGenerator.hashIdToDeleteHash(System.currentTimeMillis()),
                    description = newType.description,
                    imageBytes = newType.imageBytes,
                    anonymous = true,
                    private = false
            )
        } else {
            val author = authorizationManager.authorize(
                    token = authorization, specifiedRoles = listOf("ROLE_USER", "ROLE_MODERATOR")
            )
            Image(
                    uuid = hashGenerator.hashImageIdToUuid(System.currentTimeMillis()),
                    deleteHash = hashGenerator.hashIdToDeleteHash(System.currentTimeMillis()),
                    description = newType.description,
                    imageBytes = newType.imageBytes,
                    dateUploaded = System.currentTimeMillis(),
                    author = author,
                    private = newType.private
            )
        }
        imageRepository.save(image)
        val dto = if (authorization == null) dtoBuilder.getNewAnonImageDto(image) else dtoBuilder.getNewImageDto(image)
        return responseBuilder.buildSuccessfulResponse(200, "Image successfully uploaded", dto)
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        val image = imageRepository.getOneByUuid(idType) ?:
                throw ContentNotFoundException("Error while retrieving image. Image not found.")
        if (image.private) authorizationManager.authorize(authorization, image.author!!.name, listOf("ROLE_MODERATOR"))
        val dto = if (image.anonymous) dtoBuilder.getAnonImageDto(image) else dtoBuilder.getImageDto(image)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully retrieved image.", dto)
    }

    override fun updateEntity(idType: String, updateType: UpdateImageDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        val imageToBeEdited = imageRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while editing image description. Image not found.")
        if (authorize) authorizationManager.authorize(authorization, imageToBeEdited.author!!.name)
        if (imageToBeEdited.anonymous && updateType.private)
            throw AnonImagePrivateException("Error while editing image. Anonymous image cannot be private.")
        val editedImage = imageToBeEdited.copy(
                description = updateType.description,
                private = imageToBeEdited.private
        )
        imageRepository.save(editedImage)
        val dto = if (editedImage.anonymous) dtoBuilder.getAnonImageDto(editedImage) else dtoBuilder.getImageDto(editedImage)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully edited image.", dto)
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        val image = imageRepository.getOneByUuid(idType) ?:
                throw ContentNotFoundException("Error while deleting image. Image not found.")
        if (authorize) authorizationManager.authorize(authorization, image.author!!.name)
        imageRepository.delete(image)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully deleted image.")
    }

    override fun updateImageWithDeleteHash(uuid: String, deleteHash: String, updateImage: UpdateImageDto): ResponseEntity<ResponseDto> {
        val image = imageRepository.getOneByUuid(uuid)
                ?: throw ContentNotFoundException("Error while editing image description. Image not found.")
        if (image.deleteHash == deleteHash) {
            return updateEntity(idType = uuid, updateType = updateImage, authorize = false)
        } else {
            throw ForbiddenContentException("Forbidden. You don't have rights to do this action.")
        }
    }

    override fun deleteImageWithDeleteHash(uuid: String, deleteHash: String): ResponseEntity<ResponseDto> {
        val image = imageRepository.getOneByUuid(uuid)
                ?: throw ContentNotFoundException("Error while deleting image description. Image not found.")
        if (image.deleteHash == deleteHash) {
            return deleteEntity(idType = uuid, authorize = false)
        } else {
            throw ForbiddenContentException("Forbidden. You don't have rights to do this action.")
        }
    }

    override fun reportImage(uuid: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun like(uuid: String, authorization: String): ResponseEntity<ResponseDto> {
        val user = authorizationManager.authorize(
                token = authorization, specifiedRoles = listOf("ROLE_USER", "ROLE_MODERATOR")
        )
        val image = imageRepository.getOneByUuid(uuid)
                ?: throw ContentNotFoundException("Error while liking image. Image not found.")
        if (user.likedImages.contains(image)) {
            val newUser = user.copy(
                    likedImages = user.likedImages.minus(image)
            )
            userRepository.save(newUser)
        } else {
            val newUser = user.copy(
                    likedImages = user.likedImages.plus(image)
            )
            userRepository.save(newUser)
        }
        return responseBuilder.buildSuccessfulResponse(204, "Successfully liked/unliked image.")
    }
}