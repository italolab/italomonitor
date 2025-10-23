import axios from "axios";
import { BASE_API_URL } from "../constants/api-constants";
import type { SaveUsuarioGrupoRequest } from "./dto/request/SaveUsuarioGrupoRequest";

export class UsuarioGrupoModel {

    async createUsuarioGrupo( grupoSave : SaveUsuarioGrupoRequest ) {
        return await axios.post( BASE_API_URL + "/usuario-grupos", grupoSave, {
            withCredentials: true
        } );
    }

    async updateUsuarioGrupo( usuarioGrupoId : number, grupoSave : SaveUsuarioGrupoRequest ) {
        return await axios.put( BASE_API_URL + "/usuario-grupos/"+usuarioGrupoId, grupoSave, {
            withCredentials: true
        } );
    } 

    async filterUsuarioGrupos( nomepart : string ) {
        return await axios.get( BASE_API_URL + "/usuario-grupos?nomepart="+nomepart, {
            withCredentials: true
        } );
    }

    async getUsuarioGrupo( usuarioGrupoId : number ) {
        return await axios.get( BASE_API_URL + "/usuario-grupos/"+usuarioGrupoId+"/get", {
            withCredentials: true
        })
    }

    async getRoles( usuarioGrupoId : number ) {
        return await axios.get( BASE_API_URL + "/usuario-grupos/"+usuarioGrupoId+"/roles", {
            withCredentials: true
        } );
    }

    async vinculaRole( usuarioGrupoId : number, roleId : number ) {
        return await axios.post( BASE_API_URL + "/usuario-grupos/"+usuarioGrupoId+"/roles/"+roleId, {}, {
            withCredentials: true
        } );
    }

    async deleteRoleVinculado( usuarioGrupoId : number, roleId : number ) {
        return await axios.delete( BASE_API_URL + "/usuario-grupos/"+usuarioGrupoId+"/roles/"+roleId, {
            withCredentials: true
        } );
    }

    async deleteUsuarioGrupo( usuarioGrupoId : number ) {
        return await axios.delete( BASE_API_URL + "/usuario-grupos/"+usuarioGrupoId, {
            withCredentials: true
        } );
    }

}