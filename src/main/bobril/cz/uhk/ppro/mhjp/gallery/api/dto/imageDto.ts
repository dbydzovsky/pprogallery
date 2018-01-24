import {DataDto, DataDtoWithoutLinks} from "./baseDto";
import {ListUserDataDto} from "./UserDto";

export interface NewImageDto{
        description: string,
        imageBytes: string[],
        private: boolean
}

export interface UpdateImageDto{
        description: string ,
        private: boolean
}

export interface NewImageDataDto{
        uuid: string,
        description: string,
        imageBytes: string[],
        private: boolean,
        author: ListUserDataDto
}

export interface NewAnonImageDataDto{
        uuid: string,
        deleteHash: string,
        description: string,
        imageBytes: string[]
}

export interface ImageDataDto{
        uuid: string,
        description: string,
        imageBytes: string[],
        private: boolean,
        author: ListUserDataDto,
        likedByUsers: DataDto[],
        comments: DataDto[]
}

export interface AnonImageDataDto{
        uuid: string,
        description: string,
        imageBytes: string[],
        likedByUsers: DataDto[],
        comments: DataDto[]
}

export interface ListImageDataDto{
        uuid: string
}

export interface ImagesListDto extends DataDtoWithoutLinks{
        images: ListImageDataDto[]
}