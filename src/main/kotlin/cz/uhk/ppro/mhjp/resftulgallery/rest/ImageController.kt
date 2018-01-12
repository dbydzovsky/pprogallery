package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewImageDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateImageDto
import cz.uhk.ppro.mhjp.resftulgallery.service.ImageService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class ImageController(
        private val imageService: ImageService,
        private val headerUtil: HeaderUtil
) {

    @PostMapping("/api/i")
    fun uploadImage(@RequestBody image: NewImageDto, request: HttpServletRequest)
            = imageService.createEntity(image, headerUtil.getOptionalTokenFromHeader(request))

    @GetMapping("/api/i/{uuid}")
    fun retrieveImage(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))

    @PutMapping("/api/i/{uuid}")
    fun updateImage(@PathVariable("uuid") uuid: String, @RequestBody updateImage: UpdateImageDto, request: HttpServletRequest)
            = imageService.updateEntity(uuid, updateImage, headerUtil.getTokenFromHeader(request))

    @DeleteMapping("/api/i/{uuid}")
    fun deleteImage(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageService.deleteEntity(uuid, headerUtil.getTokenFromHeader(request))

    @PutMapping("/api/i/{uuid}/{deletehash}")
    fun updateImageWithDeleteHash(@PathVariable("uuid") uuid: String, @PathVariable("deletehash") deleteHash: String,
                                  @RequestBody updateImage: UpdateImageDto)
            = imageService.updateImageWithDeleteHash(uuid, deleteHash, updateImage)

    @DeleteMapping("/api/i/{uuid}/{deletehash}")
    fun deleteImageWithDeleteHash(@PathVariable("uuid") uuid: String, @PathVariable("deletehash") deleteHash: String)
            = imageService.deleteImageWithDeleteHash(uuid, deleteHash)

    @PostMapping("/api/i/{uuid}/like")
    fun likeImage(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageService.like(uuid, headerUtil.getTokenFromHeader(request))
}