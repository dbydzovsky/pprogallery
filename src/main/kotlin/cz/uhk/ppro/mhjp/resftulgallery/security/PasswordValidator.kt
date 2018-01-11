package cz.uhk.ppro.mhjp.resftulgallery.security

import cz.uhk.ppro.mhjp.resftulgallery.util.WeakPasswordException
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class PasswordValidator {

    companion object {
        private const val PASS_LENGTH = 6
        private const val PASS_NUMBERS = true
        private const val PASS_UPPERCASE = true
        private const val PASS_SPECIAL_SYMBOLS = false
        private const val SPECIAL_SYMBOLS = "\$&+,:;=?@#|'<>.^*()%!-"
    }

    fun hashPassword(plainPass: String, validateConstraints: Boolean = true): String {
        if (validateConstraints) validatePasswordStrength(plainPass)
        return BCrypt.hashpw(plainPass, BCrypt.gensalt(12))
    }

    fun matchPasswords(plainPass: String, hashPass: String): Boolean = BCrypt.checkpw(plainPass, hashPass)

    private fun validatePasswordStrength(pass: String) {

        val errors = mutableListOf<String>()

        if (pass.length < PASS_LENGTH) errors.add("Minimum length is $PASS_LENGTH symbols.")
        if (PASS_NUMBERS && pass.contains(Regex("[0-9]"))) errors.add("Use at least one number.")
        if (PASS_UPPERCASE && pass == pass.toLowerCase()) errors.add("Use at least one uppercase symbol.")
        if (PASS_SPECIAL_SYMBOLS && pass.contains(Regex("[$SPECIAL_SYMBOLS]"))) errors.add("Use at least one special symbol.")

        if (errors.isNotEmpty()) throw WeakPasswordException("Your password is weak. ${errors.joinToString(separator = " ")}")
    }

}