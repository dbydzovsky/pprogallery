package cz.uhk.ppro.mhjp.resftulgallery.util

class JwtMissingException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class NoAuthHeaderException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class IncorrectJwtFormatException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class IncorrectAuthHeaderFormatException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class OutdatedJwtException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class UnknownDtoException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class TokenOwnerNotFoundException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class ForbiddenContentException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class UsernameAlreadyExistsException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class ContentNotFoundException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class PasswordsDontMatchException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class NothingToDoException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)

class NoContentException(msg: String, throwable: Throwable? = null) : RuntimeException(msg, throwable)