package cz.uhk.ppro.mhjp.resftulgallery.domain

import javax.persistence.*

@Entity
data class Image(
        @Id
        val uuid: String,
        val deleteHash: String,
        val description: String,
        @Lob
        val imageBytes: ByteArray,
        val dateUploaded: Long = System.currentTimeMillis(),
        val private: Boolean,
        val anonymous: Boolean = false,
        @ManyToOne
        val author: User? = null,
        @OneToMany(mappedBy = "image")
        val comments: List<ImageComment> = listOf(),
        @ManyToMany(mappedBy = "likedImages")
        val likedByUsers: List<User> = emptyList()
)