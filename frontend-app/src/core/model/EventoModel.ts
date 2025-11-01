import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";

export class EventoModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async listByDia( dispositivoId : number, dataDia : string ) {
        return await api.get( "/eventos/"+dispositivoId+"/dia/"+dataDia );
    }

    async listByIntervalo( dispositivoId : number, dataDiaIni : string, dataDiaFim : string ) {
        return await api.get( "/eventos/"+dispositivoId+"/diaIni/"+dataDiaIni+"/diaFim/"+dataDiaFim );
    }

}