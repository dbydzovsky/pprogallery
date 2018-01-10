package cz.uhk.ppro.mhjp.resftulgallery.dto

class SubmitReportDto(
        val reason: String,
        val author: String,
        val dateSubmited: Long = System.currentTimeMillis()
)