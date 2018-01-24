import {ErrorResponseDto, SuccessResponseDto} from "../dto/baseDto";
import {httpDelete, httpGet, httpPost, httpPut} from "../http";
import {AddRemoveImagesFromGalleryDto, GalleryDataDto, SubmitGalleryDto} from "../dto/galleryDto";

export const createGallery =  (newGallery: SubmitGalleryDto,
                            success?: (response: GalleryDataDto, status: number, headers: (key: string) => string) => void,
                                 error?: (response: GalleryDataDto, status: number) => void,) => {
    httpPost("/api/g/", newGallery, success, error);
};

export const retrieveGallery =  (uuid: string,
                               success?: (response: GalleryDataDto, status: number, headers: (key: string) => string) => void,
                               error?: (response: GalleryDataDto, status: number) => void,) => {
    httpGet("/api/g/" + uuid, undefined, success, error);
};

export const editGallery =  (uuid: string,
                                 editGallery: SubmitGalleryDto,
                                 success?: (response: GalleryDataDto, status: number, headers: (key: string) => string) => void,
                                 error?: (response: GalleryDataDto, status: number) => void,) => {
    httpPut("/api/g/" + uuid, editGallery, success, error);
};
export const addImages =  (uuid: string,
                             add: AddRemoveImagesFromGalleryDto,
                             success?: (response: SuccessResponseDto, status: number, headers: (key: string) => string) => void,
                             error?: (response: ErrorResponseDto, status: number) => void,) => {
    httpPost("/api/g/"+uuid+"/add" + uuid, add, success, error);
};
export const deleteGallery =  (uuid: string,
                               remove: AddRemoveImagesFromGalleryDto,
                               success?: (response: SuccessResponseDto, status: number, headers: (key: string) => string) => void,
                               error?: (response: ErrorResponseDto, status: number) => void,) => {
    httpDelete("/api/g/" + uuid + "/remove", undefined, success, error);
};