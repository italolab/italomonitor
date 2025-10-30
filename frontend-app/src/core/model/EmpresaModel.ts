import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveEmpresaRequest } from "../model/dto/request/SaveEmpresaRequest";

export class EmpresaModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createEmpresa( empresaSave : SaveEmpresaRequest ) {
        return await api.post( "/empresas", empresaSave );
    }

    async updateEmpresa( empresaId : number, empresaSave : SaveEmpresaRequest ) {
        return await api.put( "/empresas/"+empresaId, empresaSave );
    } 

    async filterEmpresas( nomepart : string ) {
        return await api.get( "/empresas?nomepart="+nomepart );
    }

    async getEmpresa( empresaId : number ) {
        return await api.get( "/empresas/"+empresaId+"/get" );
    }

    async deleteEmpresa( empresaId : number ) {
        return await api.delete( "/empresas/"+empresaId );
    }

}