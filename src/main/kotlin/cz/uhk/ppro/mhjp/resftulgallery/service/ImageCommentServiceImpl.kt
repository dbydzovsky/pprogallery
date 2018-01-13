package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dao.ImageCommentRepository
import cz.uhk.ppro.mhjp.resftulgallery.dao.ImageRepository
import cz.uhk.ppro.mhjp.resftulgallery.domain.ImageComment
import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitCommentDto
import cz.uhk.ppro.mhjp.resftulgallery.security.AuthorizationManager
import cz.uhk.ppro.mhjp.resftulgallery.util.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ImageCommentServiceImpl(
        private val imageRepository: ImageRepository,
        private val imageCommentRepository: ImageCommentRepository,
        private val authorizationManager: AuthorizationManager,
        private val hashGenerator: HashGenerator,
        private val dtoBuilder: DtoBuilder,
        private val responseBuilder: ResponseBuilder,
        private val dtoValidator: DtoValidator
) : ImageCommentService {

    override fun createEntity(newType: SubmitCommentDto, authorization: String?, parent: String?): ResponseEntity<ResponseDto> {
        dtoValidator.validateDto(newType)
        val author = authorizationManager.authorize(
                token = authorization, specifiedRoles = listOf("ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN")
        )
        val image = imageRepository.getOneByUuid(parent!!)
                ?: throw ContentNotFoundException("Error while commenting image. Image not found.")
        if (image.private) throw CommentOfPrivateImage("Error while commenting image. You can not comment private image")
        val newComment = ImageComment(
                uuid = hashGenerator.hashCommentIdToUuid(System.currentTimeMillis()),
                content = newType.content,
                image = image,
                author = author
        )
        imageCommentRepository.save(newComment)
        val dto = dtoBuilder.getCommentDto(newComment)
        return responseBuilder.buildSuccessfulResponse(200, "Comment successfully added.", dto)
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        val comment = imageCommentRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while retrieving comment. Comment not found.")
        try {
            if (comment.image.private) authorizationManager.authorize(authorization, comment.image.author!!.name, listOf("ROLE_MODERATOR"))
        } catch (e: CustomException) {
            throw CommentOfPrivateImage("Error while retrieving comment. Comment belongs to private image.")
        }
        val dto = dtoBuilder.getCommentDto(comment)
        return responseBuilder.buildSuccessfulResponse(200, "Comment successfully retrieved.", dto)
    }

    override fun updateEntity(idType: String, updateType: SubmitCommentDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        dtoValidator.validateDto(idType)
        val commentToBeEdited = imageCommentRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while editing comment. Comment not found.")
        authorizationManager.authorize(authorization, commentToBeEdited.author.name)
        try {
            if (commentToBeEdited.image.private) authorizationManager.authorize(authorization, commentToBeEdited.image.author!!.name, listOf("ROLE_MODERATOR"))
        } catch (e: CustomException) {
            throw CommentOfPrivateImage("Error while editing comment. Comment belongs to private image.")
        }
        val editedComment = commentToBeEdited.copy(content = updateType.content)
        imageCommentRepository.save(editedComment)
        val dto = dtoBuilder.getCommentDto(editedComment)
        return responseBuilder.buildSuccessfulResponse(200, "Successfully edited comment.", dto)
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        val comment = imageCommentRepository.getOneByUuid(idType)
                ?: throw ContentNotFoundException("Error while editing comment. Comment not found.")
        authorizationManager.authorize(authorization, comment.author.name)
        try {
            if (comment.image.private) authorizationManager.authorize(authorization, comment.image.author!!.name, listOf("ROLE_MODERATOR"))
        } catch (e: CustomException) {
            throw CommentOfPrivateImage("Error while deleting comment. Comment belongs to private image.")
        }
        imageCommentRepository.delete(comment)
        return responseBuilder.buildSuccessfulResponse(204, "Successfully deleted comment.")
    }
}