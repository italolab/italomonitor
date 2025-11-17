
import axios, { AxiosError } from "axios";
import type { ErrorResponse } from "../model/dto/response/ErrorResponse";
import { format, parseISO } from "date-fns";
import { fromZonedTime } from "date-fns-tz";

import { ptBR } from 'date-fns/locale/pt-BR';

const TIMEZONE = "America/Recife";

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

export const zonedData = ( date : Date ) => {
    return fromZonedTime( date, TIMEZONE );
}

export const dataToString = ( date : Date ) => {
    return format( zonedData( date ), 'yyyy-MM-dd' );
};

export const stringToData = ( dateStr : string ) => {
    return parseISO( dateStr );
};

export const dataHoraToString = ( date : Date ) => {
    return format( zonedData( date ), 'dd/MM/yyyy HH:mm:ss' );
};

export const formataData = ( date : Date ) => {
    return format( zonedData( date ), 'dd/MM/yyyy' );
}

export const formataDataHora = ( date : Date ) => {
    return format( zonedData( date ), 'dd/MM/yyyy HH:mm:ss' );
}

export const formataAno = ( date : Date ) => {
    return format( zonedData( date ), "yyyy" );
};

export const formataAnoMes = ( date : Date ) => {
    return format( zonedData( date ), "MM/yyyy");
}

export const formataMesExtenso = ( date : Date ) => {
    return format( zonedData( date ), "MMMM", { locale: ptBR } );
}

export const formataMes = ( date : Date ) => {
    return format( zonedData( date ), "MM" );
}

export const formataMesDia = ( date : Date ) => {
    return format( zonedData( date ), "dd/MM")
}

export const formataDia = ( date : Date ) => {
    return format( zonedData( date ), "dd" );
}

export const formataDataHoraSemSegundos = ( date : Date ) => {
    return format( zonedData( date ), 'dd/MM/yyyy HH:mm' );
}

export const formataHora = ( date : Date ) => {
    return format( zonedData( date ), "HH:mm" );
}

export const formataTempo = ( tempo : number ) => {
    const horas = Math.floor( tempo / 3600 );
    const resto = tempo % 3600;

    const minutos = Math.floor( resto / 60 );
    const segundos = resto % 60;

    let tempoStr = "";
    if ( horas > 0 )
        tempoStr += (horas < 10 ? "0" : "") + horas + ":";
    if ( minutos > 0 )
        tempoStr += (minutos < 10 ? "0" : "") + minutos + ":";

    tempoStr += (segundos < 10 ? "0" : "") + segundos;

    return tempoStr;
};

export const formataMoeda = ( valor : number ) => {
    return "R$ " + valor.toFixed( 2 ).replace( '.', ',' );
};