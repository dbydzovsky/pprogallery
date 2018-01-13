package cz.uhk.ppro.mhjp.resftulgallery.domain

import javax.persistence.*

@Entity
data class Gallery(
        @Id
        val uuid: String,
        val title: String,
        val private: Boolean,
        @ManyToOne
        val author: User,
        @ManyToMany
        @JoinTable(
                name = "gallery_images",
                joinColumns = [(JoinColumn(name = "gallery_uuid"))],
                inverseJoinColumns = [(JoinColumn(name = "image_uuid"))]
        )
        val images: List<Image> = emptyList()
)