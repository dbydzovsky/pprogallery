import {DataDtoWithoutLinks} from "./baseDto";

export interface JwtDto extends DataDtoWithoutLinks {
        token: string,
        expires: number
}