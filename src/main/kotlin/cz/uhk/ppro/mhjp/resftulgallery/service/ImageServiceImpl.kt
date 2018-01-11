package cz.uhk.ppro.mhjp.resftulgallery.service

import cz.uhk.ppro.mhjp.resftulgallery.dto.NewImageDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.SubmitReportDto
import cz.uhk.ppro.mhjp.resftulgallery.dto.UpdateImageDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl : ImageService {

    override fun createEntity(newType: NewImageDto, authorization: String?): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun readEntity(idType: String, authorization: String?): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateEntity(idType: String, updateType: UpdateImageDto, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteEntity(idType: String, authorization: String?, authorize: Boolean): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateImageWithDeleteHash(uuid: String, updateImage: UpdateImageDto): ResponseEntity<ResponseDto> {
        return updateEntity(uuid, updateImage)
    }

    override fun deleteImageWithDeleteHash(uuid: String): ResponseEntity<ResponseDto> {
        return deleteEntity(uuid)
    }

    override fun reportImage(uuid: String, report: SubmitReportDto, authorization: String): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun like(uuid: String, authorization: String): ResponseEntity<ResponseDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}