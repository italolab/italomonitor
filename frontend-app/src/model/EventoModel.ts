import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";

export class EventoModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async listByDia( dispositivoId : number, dataDia : string ) {
        return await api.get( "/eventos/"+dispositivoId+"/dia/"+dataDia );
    }

}