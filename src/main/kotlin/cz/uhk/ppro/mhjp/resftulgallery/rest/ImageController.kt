package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewImageDto
import cz.uhk.ppro.mhjp.resftulgallery.service.ImageService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class ImageController(
        private val imageService: ImageService,
        private val headerUtil: HeaderUtil
) {

    @PostMapping("/i/")
    fun uploadImage(@RequestBody image: NewImageDto, request: HttpServletRequest)
            = imageService.createEntity(image, headerUtil.getOptionalTokenFromHeader(request))

    @GetMapping("/i/{uuid}")
    fun retrieveImage(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))

    @GetMapping("/i/")
    fun getAll() = imageService.getAll()

}