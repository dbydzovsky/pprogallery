import {httpPost} from "../http";
import {HttpStatus} from "../httpStatus";
import * as b from "bobril";

export interface IResult {
    imageSrc: string;
    imageName: string;
}

const success = (result: IResult) => {
    const imageName = result.imageName;
    const imageSrc = result.imageSrc;

    // do here whatever with your image
};

const error = (errorMessage: any, status: number) => {
    if (status === HttpStatus.Forbidden) {
        // redirect on forbidden error
        b.runTransition(b.createRedirectPush("login"));
    } else {
        // Do whatever with error message
        console.log(errorMessage)
    }
};

httpPost(
    "allImages",
    {id: 1535},
    success,
    error);