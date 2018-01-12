package cz.uhk.ppro.mhjp.resftulgallery.security

import cz.uhk.ppro.mhjp.resftulgallery.domain.User

interface AuthorizationManager {

    fun authorize(token: String?, entityOwner: String? = null, specifiedRoles: List<String> = listOf()): User

}