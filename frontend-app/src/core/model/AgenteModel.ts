import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveAgenteRequest } from "./dto/request/SaveAgenteRequest";

export class AgenteModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createAgente( agenteSave : SaveAgenteRequest, empresaId : number ) {
        return await api.post( "/agentes/empresa/"+empresaId, agenteSave );
    }

    async updateAgente( agenteId : number, agenteSave : SaveAgenteRequest, empresaId : number ) {
        return await api.put( "/agentes/"+agenteId+"/empresa/"+empresaId, agenteSave );
    } 

    async filterAgentes( nomepart : string, empresaId : number ) {
        return await api.get( "/agentes/empresa/"+empresaId+"?nomepart="+nomepart );
    }

    async getAgente( agenteId : number ) {
        return await api.get( "/agentes/"+agenteId+"/get" );
    }

    async deleteAgente( agenteId : number ) {
        return await api.delete( "/agentes/"+agenteId );
    }

}