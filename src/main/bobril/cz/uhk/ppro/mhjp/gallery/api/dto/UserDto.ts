import {DataDto} from "./baseDto";

export interface NewUserDto {
        username: string ,
        password: string ,
        repeatPassword: string
}

export interface UpdateUserDto{
        username: string ,
        name: string ,
        private: boolean
}

export interface UpdateUserPasswordDto{
        oldPassword: string,
        newPassword: string,
        repeatNewPassword: string
}

export interface UserDataDto extends DataDto{
        username: string,
        name: string,
        dateJoined: number,
        enabled: boolean,
        private: boolean,
        images: DataDto[]
}

export interface ListUserDataDto extends DataDto{
        username: string
}