import { MediaType, IHeader, IRequestOptions, request } from './api';

// todo adjust base URL
const baseUrl: string = '/rest/web/';

export function httpPost<TData, TResponse, TErrorResponse>(
    endpoint: string,
    data?: TData,
    success?: (response: TResponse, status: number, headers: (key: string) => string) => void,
    error?: (response: TResponse, status: number) => void,
    options?: IRequestOptions
) {
    request<TResponse, TErrorResponse>({
        method: 'POST',
        url: urlBuilder(endpoint),
        data: JSON.stringify(data),
        errorCallback: error,
        successCallback: success,
        headers: [
            getAcceptHeader(options),
            getContentTypeHeader(options),
            getCsrfTokenHeader()
        ],
        options: options
    });
}

export function urlBuilder(endpoint: string): string {
    return baseUrl + endpoint;
}

function getCsrfTokenHeader(): IHeader {
    return { key: 'X-CSRF-TOKEN', value: 'BUHEHE' }; // todo your authentication token
}

function getAcceptHeader(options?: IRequestOptions): IHeader {
    const acceptHeader = { key: 'Accept', value: MediaType.applicationJson };
    return acceptHeader;
}

function getContentTypeHeader(_options: IRequestOptions | undefined): IHeader {
    return { key: 'Content-Type', value: MediaType.applicationJson };
}