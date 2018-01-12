package cz.uhk.ppro.mhjp.resftulgallery.domain

import javax.persistence.*

@Entity
data class ImageComment(
        @Id
        val uuid: String,
        @ManyToOne
        val author: User,
        @ManyToOne
        val image: Image,
        val content: String
)