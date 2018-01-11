package cz.uhk.ppro.mhjp.resftulgallery.util

sealed class CustomException(msg: String, throwable: Throwable?) : RuntimeException(msg, throwable)

class IncompleteSubmitedDtoException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class JwtMissingException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class NoAuthHeaderException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class IncorrectJwtFormatException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class IncorrectAuthHeaderFormatException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class OutdatedJwtException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class UnknownDtoException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class TokenOwnerNotFoundException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class ForbiddenContentException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class UsernameAlreadyExistsException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class ContentNotFoundException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class PasswordsDontMatchException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class WeakPasswordException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class NothingToDoException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)

class NoContentException(msg: String, throwable: Throwable? = null) : CustomException(msg, throwable)