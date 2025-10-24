import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { CreateUsuarioRequest } from "./dto/request/CreateUsuarioRequest";
import type { UpdateUsuarioRequest } from "./dto/request/UpdateUsuarioRequest";

export class UsuarioModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createUsuario( usuarioSave : CreateUsuarioRequest ) {
        return await api.post( "/usuarios", usuarioSave );
    }

    async updateUsuario( usuarioId : number, usuarioSave : UpdateUsuarioRequest ) {
        return await api.put( "/usuarios/"+usuarioId, usuarioSave );
    } 

    async filterUsuarios( nomepart : string ) {
        return await api.get( "/usuarios?nomepart="+nomepart );
    }

    async getUsuario( usuarioId : number ) {
        return await api.get( "/usuarios/"+usuarioId+"/get" );
    }

    async getGrupos( usuarioId : number ) {
        return await api.get( "/usuarios/"+usuarioId+"/grupos" );
    }

    async vinculaGrupo( usuarioId : number, usuarioGrupoId : number ) {
        return await api.post( "/usuarios/"+usuarioId+"/grupos/"+usuarioGrupoId, {} );
    }

    async deleteGrupoVinculado( usuarioId : number, usuarioGrupoId : number ) {
        return await api.delete( "/usuarios/"+usuarioId+"/grupos/"+usuarioGrupoId );
    }

    async deleteUsuario( usuarioId : number ) {
        return await api.delete( "/usuarios/"+usuarioId );
    }

}