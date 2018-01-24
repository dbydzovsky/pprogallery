import {DataDto} from "./baseDto";
import {ListImageDataDto} from "./imageDto";
import {ListUserDataDto} from "./UserDto";

export interface SubmitCommentDto {
     content: string
}

export interface CommentDataDto extends DataDto {
        uuid: string,
        content: string,
        image: ListImageDataDto,
        author: ListUserDataDto
}

export interface ListCommentDataDto extends DataDto {
        uuid: string
}