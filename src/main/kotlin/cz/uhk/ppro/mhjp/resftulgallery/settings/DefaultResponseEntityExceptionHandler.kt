package cz.uhk.ppro.mhjp.resftulgallery.settings

import cz.uhk.ppro.mhjp.resftulgallery.dto.ResponseDto
import cz.uhk.ppro.mhjp.resftulgallery.util.*
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class DefaultResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(RuntimeException::class)])
    protected fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<ResponseDto> {
        return buildErrorResponse(
                when (ex) {
                    is IncorrectJwtFormatException,
                    is UnknownDtoException,
                    is NothingToDoException,
                    is IncorrectAuthHeaderFormatException-> 400
                    is JwtMissingException,
                    is OutdatedJwtException,
                    is TokenOwnerNotFoundException,
                    is NoAuthHeaderException -> 401
                    is ForbiddenContentException -> 403
                    is ContentNotFoundException -> 404
                    is HttpRequestMethodNotSupportedException -> 405
                    is UsernameAlreadyExistsException -> 409
                    is PasswordsDontMatchException,
                    is NoContentException -> 422
                    else -> 500
                },
                ex.message!!
        )
    }

}