import {DataDto} from "./baseDto";
import {ListUserDataDto} from "./UserDto";

export interface SubmitGalleryDto {
        title: string;
        private: boolean;
        images: string[]
}

export interface AddRemoveImagesFromGalleryDto{
        images: string[]
}

export interface GalleryDataDto extends DataDto {
        uuid: string,
        title: string,
        private: boolean,
        author: ListUserDataDto,
        images: DataDto[]
}

export interface ListGalleryDataDto extends DataDto {
        uuid: string
}

