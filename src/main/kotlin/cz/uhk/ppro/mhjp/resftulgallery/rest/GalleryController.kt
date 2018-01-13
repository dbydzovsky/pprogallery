package cz.uhk.ppro.mhjp.resftulgallery.rest

import cz.uhk.ppro.mhjp.resftulgallery.dto.AddRemoveImagesFromGalleryDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitGalleryDto
import cz.uhk.ppro.mhjp.resftulgallery.service.GalleryService
import cz.uhk.ppro.mhjp.resftulgallery.util.HeaderUtil
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class GalleryController(
        private val galleryService: GalleryService,
        private val headerUtil: HeaderUtil
) {

    @PostMapping("/api/g")
    fun createGallery(@RequestBody newGallery: SubmitGalleryDto, request: HttpServletRequest)
            = galleryService.createEntity(newGallery, headerUtil.getTokenFromHeader(request))

    @GetMapping("/api/g/{uuid}")
    fun retrieveGallery(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = galleryService.readEntity(uuid, headerUtil.getOptionalTokenFromHeader(request))

    @PutMapping("/api/g/{uuid}")
    fun editGallery(@PathVariable("uuid") uuid: String, @RequestBody editGallery: SubmitGalleryDto, request: HttpServletRequest)
            = galleryService.updateEntity(uuid, editGallery, headerUtil.getTokenFromHeader(request))

    @DeleteMapping("/api/g/{uuid}")
    fun deleteGallery(@PathVariable("uuid") uuid: String, request: HttpServletRequest)
            = galleryService.deleteEntity(uuid, headerUtil.getTokenFromHeader(request))

    @PostMapping("/api/g/{uuid}/add")
    fun addImages(@PathVariable("uuid") uuid: String, @RequestBody add: AddRemoveImagesFromGalleryDto, request: HttpServletRequest)
            = galleryService.addImages(uuid, add, headerUtil.getTokenFromHeader(request))

    @DeleteMapping("/api/g/{uuid}/remove")
    fun removeImages(@PathVariable("uuid") uuid: String, @RequestBody remove: AddRemoveImagesFromGalleryDto, request: HttpServletRequest)
            = galleryService.removeImages(uuid, remove, headerUtil.getTokenFromHeader(request))

}