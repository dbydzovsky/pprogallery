export enum HttpStatus {
    NetworkError = 0,
    Ok = 200,
    NoContent = 204,
    ResetContent = 205,
    BadRequest = 400,
    NotAuthorized = 401,
    Forbidden = 403,
    NotFound = 404,
    Locked = 423,
    TooManyRequests = 429,
    UnprocessableEntity = 422,
    InternalError = 500,
    ServiceUnavailable = 503
}
