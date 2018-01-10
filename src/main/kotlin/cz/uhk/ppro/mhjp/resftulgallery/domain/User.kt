package cz.uhk.ppro.mhjp.resftulgallery.domain

import javax.persistence.*

@Entity
data class User(
        @Id val username: String,
        val name: String,
        val password: String,
        val dateJoined: Long = System.currentTimeMillis(),
        val enabled: Boolean = true,
        val private: Boolean = false,
        @ManyToMany @JoinTable(
                name = "users_role",
                joinColumns = [(JoinColumn(name = "user_username"))],
                inverseJoinColumns = [(JoinColumn(name = "role_id"))]
        )
        val roles: List<Role>
)