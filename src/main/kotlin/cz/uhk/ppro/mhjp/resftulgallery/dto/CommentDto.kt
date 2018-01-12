package cz.uhk.ppro.mhjp.resftulgallery.dto

class SubmitCommentDto(
        val content: String = ""
)

open class CommentDataDto(
        val uuid: String,
        val content: String,
        val image: ListImageDataDto,
        val author: ListUserDataDto
) : DataDto()

open class ListCommentDataDto(
        val uuid: String
) : DataDto()