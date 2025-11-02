import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveDispositivoRequest } from "../model/dto/request/SaveDispositivoRequest";

export class DispositivoModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createDispositivo( dispositivoSave : SaveDispositivoRequest ) {
        return await api.post( "/dispositivos", dispositivoSave );
    }

    async updateDispositivo( dispositivoId : number, dispositivoSave : SaveDispositivoRequest ) {
        return await api.put( "/dispositivos/"+dispositivoId, dispositivoSave );
    } 

    async filterDispositivos( empresaId : number, hostpart : string, nomepart : string, localpart : string ) {
        return await api.get( "/dispositivos/empresa/"+empresaId+"?hostpart="+hostpart+"&nomepart="+nomepart+"&localpart="+localpart );
    }

    async getDispositivo( dispositivoId : number ) {
        return await api.get( "/dispositivos/"+dispositivoId+"/get" );
    }

    async deleteDispositivo( dispositivoId : number ) {
        return await api.delete( "/dispositivos/"+dispositivoId );
    }

}