package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitCommentDto
import cz.uhk.ppro.mhjp.resftulgallery.service.ImageCommentService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class ImageCommentController(
        private val imageCommentService: ImageCommentService,
        private val headerUtil: HeaderUtil
) {

    @PostMapping("/api/i/{image_uuid}/comment")
    fun addComment(@PathVariable("image_uuid") imageUuid: String, @RequestBody comment: SubmitCommentDto, request: HttpServletRequest)
            = imageCommentService.createEntity(comment, headerUtil.getTokenFromHeader(request), imageUuid)

    @GetMapping("/api/c/{uuid}")
    fun getComment(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageCommentService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))

    @PutMapping("/api/c/{uuid}")
    fun editComment(@PathVariable("uuid") uuid: String, @RequestBody comment: SubmitCommentDto, request: HttpServletRequest)
            = imageCommentService.updateEntity(uuid, comment, headerUtil.getTokenFromHeader(request))

    @DeleteMapping("/api/c/{uuid}")
    fun deleteComment(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageCommentService.deleteEntity(uuid, headerUtil.getTokenFromHeader(request))

}