
import axios, { AxiosError } from "axios";
import type { ErrorResponse } from "../model/dto/response/ErrorResponse";

export const extractErrorMessage = ( error : unknown ) => {
    if ( axios.isAxiosError( error ) ) {
        const err = error as AxiosError;
        if ( err.status === 403 ) {
            return "Você não tem permissão para acessar esse recurso.";
        }
        if ( err.response ) {
            const data = err.response.data as ErrorResponse;
            return data.message;
        }
    }    
    return "Não foi possível conectar com o sistema.";    
};