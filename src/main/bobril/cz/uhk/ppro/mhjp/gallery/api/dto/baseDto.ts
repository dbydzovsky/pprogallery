
export interface DataDtoBase {

}

export interface DataDto extends DataDtoBase {

}

export interface DataDtoWithoutLinks extends DataDtoBase {

}

// Response DTOs -> these DTOs contains additional response info

export interface ResponseDto {
    responseCode: number,
    success: boolean
}

export interface SuccessResponseDto extends ResponseDto {
    responseCode: number,
    message: string,
    data?: DataDtoBase
}

export interface ErrorResponseDto extends ResponseDto {
    responseCode: number,
    message: string
}