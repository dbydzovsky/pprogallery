package cz.uhk.ppro.mhjp.resftulgallery.domain

import javax.persistence.*

@Entity
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = 0,
        val name: String,
        @ManyToMany(mappedBy = "roles")
        val users: List<User> = emptyList()
)