package cz.uhk.ppro.mhjp.resftulgallery.domain

import javax.persistence.*

@Entity
data class Image(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val imageId: Long = 0,
        val uuid: String,
        val description: String,
        val deleteHash: String,
        @Lob
        val imageBytes: ByteArray,
        val dateUploaded: Long,
        @ManyToOne
        val author: User,
        @ManyToMany(mappedBy = "likes") val likedByUsers: List<User> = emptyList()
)