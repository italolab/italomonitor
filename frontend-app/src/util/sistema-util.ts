
import axios, { AxiosError } from "axios";
import type { ErrorResponse } from "../model/dto/response/ErrorResponse";
import { format, parseISO } from "date-fns";

export const extractErrorMessage = ( error : unknown ) => {
    if ( axios.isAxiosError( error ) ) {
        const err = error as AxiosError;
        if ( err.status === 403 ) {
            return "Seu token expirou ou você não tem permissão para acessar esse recurso. Faça login novamente!";
        }
        if ( err.response ) {
            const data = err.response.data as ErrorResponse;
            return data.message;
        }
    }    
    return "Não foi possível conectar com o sistema.";    
};

export const dataToString = ( date : Date ) => {
    return format( date, 'yyyy-MM-dd' );
};

export const stringToData = ( dateStr : string ) => {
    return parseISO( dateStr );
};

export const dataHoraToString = ( date : Date ) => {
    return format( date, 'dd/MM/yyyy HH:mm:ss' );
};

export const formataData = ( date : Date ) => {
    return format( date, 'dd/MM/yyyy' );
}

export const formataDataHora = ( date : Date ) => {
    //alert( new Date( date ) );
    return format( date, 'dd/MM/yyyy HH:mm:ss' );
}

export const formataEmMinutos = ( tempo : number ) => {
    return Math.floor( tempo / 60 ) + "min";
};