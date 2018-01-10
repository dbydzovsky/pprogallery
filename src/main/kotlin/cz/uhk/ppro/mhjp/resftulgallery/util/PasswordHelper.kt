package cz.uhk.ppro.mhjp.resftulgallery.util

import org.mindrot.jbcrypt.BCrypt

fun hashPassword(plainPass: String): String = BCrypt.hashpw(plainPass, BCrypt.gensalt(12))

fun matchPasswords(plainPass: String, hashPass: String): Boolean = BCrypt.checkpw(plainPass, hashPass)
