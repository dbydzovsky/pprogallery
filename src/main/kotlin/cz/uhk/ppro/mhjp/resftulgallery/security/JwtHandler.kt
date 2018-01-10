package cz.uhk.ppro.mhjp.resftulgallery.security

interface JwtHandler {

    fun generateToken(user: String): Pair<String, Int>

    fun validateToken(token: String): String

}