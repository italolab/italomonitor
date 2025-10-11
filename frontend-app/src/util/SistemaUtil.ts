
import axios, { AxiosError } from "axios";
import type { ErrorResponse } from "../model/dto/response/ErrorResponse";

export const extractErrorMessage = ( error : unknown ) => {
    if ( axios.isAxiosError( error ) ) {
        const err = error as AxiosError;
        if ( err.response ) {
            const data = err.response.data as ErrorResponse;
            return data.message;
        }
    }    
    return "Não foi possível conectar com o sistema.";    
};