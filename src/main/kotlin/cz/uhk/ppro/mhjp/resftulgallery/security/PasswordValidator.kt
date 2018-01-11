package cz.uhk.ppro.mhjp.resftulgallery.security

import cz.uhk.ppro.mhjp.resftulgallery.util.WeakPasswordException
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class PasswordValidator {

    private var usePasswordConstraints = true
    private var passLength = 6
    private var passNumbers = true
    private var passUppercase = true
    private var passSpecialSymbols = false
    private var specialSymbols = "\$&+,:;=?@#|'<>.^*()%!-"

    fun hashPassword(plainPass: String, validateConstraints: Boolean = true): String {
        if (validateConstraints) validatePasswordStrength(plainPass)
        return BCrypt.hashpw(plainPass, BCrypt.gensalt(12))
    }

    fun matchPasswords(plainPass: String, hashPass: String): Boolean = BCrypt.checkpw(plainPass, hashPass)

    private fun validatePasswordStrength(pass: String) {

        if (usePasswordConstraints) {
            val errors = mutableListOf<String>()

            if (pass.length < passLength) errors.add("Minimum length is $passLength symbols.")
            if (passNumbers && !pass.contains(Regex("[0-9]"))) errors.add("Use at least one number.")
            if (passUppercase && pass == pass.toLowerCase()) errors.add("Use at least one uppercase symbol.")
            if (passSpecialSymbols && !pass.contains(Regex("[$specialSymbols]"))) errors.add("Use at least one special symbol.")

            if (errors.isNotEmpty()) throw WeakPasswordException("Your password is not strong enough. ${errors.joinToString(separator = " ")}")
        }

    }

}