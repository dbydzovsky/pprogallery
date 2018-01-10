package cz.uhk.ppro.mhjp.resftulgallery.security

import cz.uhk.ppro.mhjp.resftulgallery.util.IncorrectJwtFormatException
import cz.uhk.ppro.mhjp.resftulgallery.util.OutdatedJwtException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtHandlerImpl : JwtHandler {

    companion object {
        private val JWT_SECRET = "YQL1dgwvTcoNUVM72308Wd9XhuSItkBD7xRphfHRRWdGE3uP31eCunznm4pss0sK"
        private val JWT_EXPIRATION = 3600000  //1 hour
    }

    override fun generateToken(user: String): Pair<String, Int> {
        val token = Jwts.builder()
                .setSubject(user)
                .setExpiration(Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET.toByteArray())
                .compact()
        return "Bearer $token" to JWT_EXPIRATION
    }

    override fun validateToken(token: String): String {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET.toByteArray())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .body
                    .subject
        } catch (e: MalformedJwtException) {
            throw IncorrectJwtFormatException("Error. Token format is invalid.", e)
        } catch (e: ExpiredJwtException) {
            throw OutdatedJwtException("Unauthorized. Token has expired, please authenticate with newer token.", e)
        }
    }
}