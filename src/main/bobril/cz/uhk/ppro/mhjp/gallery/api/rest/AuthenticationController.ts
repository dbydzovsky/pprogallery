import {httpGet, httpPost} from "../http";
import {JwtDto} from "../dto/jwtDto";
import {ListUserDataDto} from "../dto/UserDto";
import {IHeader, IRequestOptions} from "../api";

// call this in login page with credentials in header.. Then save the token to cookie so each request can be authenticated automatically
export const getToken = (authenticationBase64: string,
                        success?: (response: JwtDto, status: number, headers: (key: string) => string) => void,
                                     error?: (response: JwtDto, status: number) => void,) => {
    httpPost("/api/auth/token", undefined, success, error, {
        headers: [
            {
                key: "TODO - name of the authentication token",
                value: authenticationBase64
            } as IHeader
        ]
    } as IRequestOptions);
};

export const getTokenOwner =  (success?: (response: ListUserDataDto, status: number, headers: (key: string) => string) => void,
                                 error?: (response: ListUserDataDto, status: number) => void,) => {
    httpGet("/api/auth/token", undefined, success, error);
};