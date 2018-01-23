

export function request<TResponse, TErrorResponse>(requestConfig: IRequestConfig<TResponse, TErrorResponse>) {
    const req = new XMLHttpRequest();
    const url = requestConfig.url;
    const async = false;
    req.open(requestConfig.method, url, async);
    setHeaders(req, requestConfig.headers);

    req.onload = onLoadFunc<TResponse, TErrorResponse>(req, requestConfig);
    req.onerror = onErrorFunc<TResponse, TErrorResponse>(req, requestConfig);
    req.send(requestConfig.data);
}


export interface IHeader {
    key: string;
    value: string;
}

export enum MediaType {
    applicationJson = "application/json;odata=verbose;charset=UTF-8",
    octetStream = "application/octet-stream",
    textPlain = "text/plain"
}

export interface IRequestOptions {
    doNotTransformMessage?: boolean;
}

export interface IRequestConfig<TResponse, TErrorResponse> {
    method: "POST" | "GET" | "PUT" | "DELETE";
    url: string;
    headers: IHeader[];
    data?: string | object;
    successCallback?: (response: TResponse, status: number, headers: (key: string) => string) => void;
    errorCallback?: (response: TResponse, status: number) => void;
    options?: IRequestOptions;
}


function setHeaders(req: XMLHttpRequest, headers: IHeader[]) {
    headers.forEach(header => {
        req.setRequestHeader(header.key, header.value);
    });
}

export function onLoadFunc<TResponse, TErrorResponse>(request: XMLHttpRequest, requestConfig: IRequestConfig<TResponse, TErrorResponse>) {
    return () => {
        if (request.status >= 200 && request.status < 300) {
            if (requestConfig.successCallback) {
                requestConfig.successCallback(
                    requestConfig.options && !requestConfig.options.doNotTransformMessage
                        ? JSON.parse(request.response)
                        : request.response,
                    request.status,
                    (key: string): string => {
                        let header: string | null = request.getResponseHeader(key);
                        return header !== null ? header : "";
                    }
                );
            }
        } else {
            onErrorFunc<TResponse, TErrorResponse>(request, requestConfig)();
        }
    };
}

export function onErrorFunc<TResponse, TErrorResponse>(request: XMLHttpRequest, requestConfig: IRequestConfig<TResponse, TErrorResponse>) {
    return () => {
        let response = requestConfig.options && !requestConfig.options.doNotTransformMessage
            ? JSON.parse(request.response)
            : request.response;
        if (requestConfig.errorCallback) {
           const handler = requestConfig.errorCallback;
           handler(response, request.status);
        }
    };
}