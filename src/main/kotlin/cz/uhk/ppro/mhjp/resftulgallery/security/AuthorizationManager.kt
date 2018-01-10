package cz.uhk.ppro.mhjp.resftulgallery.security

interface AuthorizationManager {

    fun authorize(token: String?, entityOwner: String? = null, specifiedRoles: List<String> = listOf())

}