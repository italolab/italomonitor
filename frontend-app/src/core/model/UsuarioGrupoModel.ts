import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveUsuarioGrupoRequest } from "../model/dto/request/SaveUsuarioGrupoRequest";

export class UsuarioGrupoModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createUsuarioGrupo( grupoSave : SaveUsuarioGrupoRequest ) {
        return await api.post( "/usuario-grupos", grupoSave, {
            withCredentials: true
        } );
    }

    async updateUsuarioGrupo( usuarioGrupoId : number, grupoSave : SaveUsuarioGrupoRequest ) {
        return await api.put( "/usuario-grupos/"+usuarioGrupoId, grupoSave, {
            withCredentials: true
        } );
    } 

    async filterUsuarioGrupos( nomepart : string ) {
        return await api.get( "/usuario-grupos?nomepart="+nomepart, {
            withCredentials: true
        } );
    }

    async getUsuarioGrupo( usuarioGrupoId : number ) {
        return await api.get( "/usuario-grupos/"+usuarioGrupoId+"/get", {
            withCredentials: true
        })
    }

    async getRoles( usuarioGrupoId : number ) {
        return await api.get( "/usuario-grupos/"+usuarioGrupoId+"/roles", {
            withCredentials: true
        } );
    }

    async vinculaRole( usuarioGrupoId : number, roleId : number ) {
        return await api.post( "/usuario-grupos/"+usuarioGrupoId+"/roles/"+roleId, {}, {
            withCredentials: true
        } );
    }

    async deleteRoleVinculado( usuarioGrupoId : number, roleId : number ) {
        return await api.delete( "/usuario-grupos/"+usuarioGrupoId+"/roles/"+roleId, {
            withCredentials: true
        } );
    }

    async deleteUsuarioGrupo( usuarioGrupoId : number ) {
        return await api.delete( "/usuario-grupos/"+usuarioGrupoId, {
            withCredentials: true
        } );
    }

}