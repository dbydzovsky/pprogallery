package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.service.ImageService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class ImageController(
        private val imageService: ImageService,
        private val headerUtil: HeaderUtil
) {

    @GetMapping("/i/{uuid}")
    fun retrieveImage(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = imageService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))

}