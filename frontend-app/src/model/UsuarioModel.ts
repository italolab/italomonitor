import axios from "axios";
import { BASE_API_URL } from "../constants/api-constants";
import type { CreateUsuarioRequest } from "./dto/request/CreateUsuarioRequest";
import type { UpdateUsuarioRequest } from "./dto/request/UpdateUsuarioRequest";

export class UsuarioModel {

    async createUsuario( usuarioSave : CreateUsuarioRequest ) {
        return await axios.post( BASE_API_URL + "/usuarios", usuarioSave, {
            withCredentials: true
        } );
    }

    async updateUsuario( usuarioId : number, usuarioSave : UpdateUsuarioRequest ) {
        return await axios.put( BASE_API_URL + "/usuarios/"+usuarioId, usuarioSave, {
            withCredentials: true
        } );
    } 

    async filterUsuarios( nomepart : string ) {
        return await axios.get( BASE_API_URL + "/usuarios?nomepart="+nomepart, {
            withCredentials: true
        } );
    }

    async getUsuario( usuarioId : number ) {
        return await axios.get( BASE_API_URL + "/usuarios/"+usuarioId+"/get", {
            withCredentials: true
        })
    }

    async getGrupos( usuarioId : number ) {
        return await axios.get( BASE_API_URL + "/usuarios/"+usuarioId+"/grupos", {
            withCredentials: true
        } );
    }

    async vinculaGrupo( usuarioId : number, usuarioGrupoId : number ) {
        return await axios.post( BASE_API_URL + "/usuarios/"+usuarioId+"/grupos/"+usuarioGrupoId, {}, {
            withCredentials: true
        } );
    }

    async deleteGrupoVinculado( usuarioId : number, usuarioGrupoId : number ) {
        return await axios.delete( BASE_API_URL + "/usuarios/"+usuarioId+"/grupos/"+usuarioGrupoId, {
            withCredentials: true
        } );
    }

    async deleteUsuario( usuarioId : number ) {
        return await axios.delete( BASE_API_URL + "/usuarios/"+usuarioId, {
            withCredentials: true
        } );
    }

}