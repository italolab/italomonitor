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

    async listDispositivos( empresaId : number ) {
        return await api.get( "/dispositivos/empresa/"+empresaId );
    }

    async getDispositivo( dispositivoId : number ) {
        return await api.get( "/dispositivos/"+dispositivoId+"/get-of-empresa" );
    }

    async deleteDispositivo( dispositivoId : number ) {
        return await api.delete( "/dispositivos/"+dispositivoId );
    }

}