


// todo
// @RestController
// export interface ImageCommentController(
//         private imageCommentService: ImageCommentService,
//         private headerUtil: HeaderUtil
// ) {
//
//     @PostMapping("/api/i/{image_uuid}/comment")
//     fun addComment(@PathVariable("image_uuid") imageUuid: string, @RequestBody comment: SubmitCommentDto, request: HttpServletRequest)
//             = imageCommentService.createEntity(comment, headerUtil.getTokenFromHeader(request), imageUuid)
//
//     @GetMapping("/api/c/{uuid}")
//     fun getComment(@PathVariable("uuid") uuid: string, request: HttpServletRequest)
//             = imageCommentService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))
//
//     @PutMapping("/api/c/{uuid}")
//     fun editComment(@PathVariable("uuid") uuid: string, @RequestBody comment: SubmitCommentDto, request: HttpServletRequest)
//             = imageCommentService.updateEntity(uuid, comment, headerUtil.getTokenFromHeader(request))
//
//     @DeleteMapping("/api/c/{uuid}")
//     fun deleteComment(@PathVariable("uuid") uuid: string, request: HttpServletRequest)
//             = imageCommentService.deleteEntity(uuid, headerUtil.getTokenFromHeader(request))
//
// }