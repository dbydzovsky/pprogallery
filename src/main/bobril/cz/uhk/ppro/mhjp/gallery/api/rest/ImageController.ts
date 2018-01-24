

// todo
// @RestController
// export interface ImageController(
//         private imageService: ImageService,
//         private headerUtil: HeaderUtil
// ) {
//
//     @PostMapping("/api/i")
//     fun uploadImage(@RequestBody image: NewImageDto, request: HttpServletRequest)
//             = imageService.createEntity(image, headerUtil.getOptionalTokenFromHeader(request))
//
//     @GetMapping("/api/i/{uuid}")
//     fun retrieveImage(@PathVariable("uuid") uuid: string, request: HttpServletRequest)
//             = imageService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))
//
//     @PutMapping("/api/i/{uuid}")
//     fun updateImage(@PathVariable("uuid") uuid: string, @RequestBody updateImage: UpdateImageDto, request: HttpServletRequest)
//             = imageService.updateEntity(uuid, updateImage, headerUtil.getTokenFromHeader(request))
//
//     @DeleteMapping("/api/i/{uuid}")
//     fun deleteImage(@PathVariable("uuid") uuid: string, request: HttpServletRequest)
//             = imageService.deleteEntity(uuid, headerUtil.getTokenFromHeader(request))
//
//     @PutMapping("/api/i/{uuid}/{deletehash}")
//     fun updateImageWithDeleteHash(@PathVariable("uuid") uuid: string, @PathVariable("deletehash") deleteHash: string,
//                                   @RequestBody updateImage: UpdateImageDto)
//             = imageService.updateImageWithDeleteHash(uuid, deleteHash, updateImage)
//
//     @DeleteMapping("/api/i/{uuid}/{deletehash}")
//     fun deleteImageWithDeleteHash(@PathVariable("uuid") uuid: string, @PathVariable("deletehash") deleteHash: string)
//             = imageService.deleteImageWithDeleteHash(uuid, deleteHash)
//
//     @PostMapping("/api/i/{uuid}/like")
//     fun likeImage(@PathVariable("uuid") uuid: string, request: HttpServletRequest)
//             = imageService.like(uuid, headerUtil.getTokenFromHeader(request))
// }