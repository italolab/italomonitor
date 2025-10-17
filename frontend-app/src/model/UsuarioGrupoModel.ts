import axios from "axios";
import { BASE_URL } from "../constants/api-constants";
import type { SaveUsuarioGrupoRequest } from "./dto/request/SaveUsuarioGrupoRequest";

export class UsuarioGrupoModel {

    async createUsuarioGrupo( grupoSave : SaveUsuarioGrupoRequest, token : string ) {
        return await axios.post( BASE_URL + "/usuario-grupos", grupoSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    }

    async updateUsuarioGrupo( usuarioGrupoId : number, grupoSave : SaveUsuarioGrupoRequest, token : string ) {
        return await axios.put( BASE_URL + "/usuario-grupos/"+usuarioGrupoId, grupoSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    } 

    async filterUsuarioGrupos( nomepart : string, token : string ) {
        return await axios.get( BASE_URL + "/usuario-grupos?nomepart="+nomepart, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async getUsuarioGrupo( usuarioGrupoId : number, token : string ) {
        return await axios.get( BASE_URL + "/usuario-grupos/"+usuarioGrupoId+"/get", {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        })
    }

    async getRoles( usuarioGrupoId : number, token : string ) {
        return await axios.get( BASE_URL + "/usuario-grupos/"+usuarioGrupoId+"/roles", {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async vinculaRole( usuarioGrupoId : number, roleId : number, token : string ) {
        return await axios.post( BASE_URL + "/usuario-grupos/"+usuarioGrupoId+"/roles/"+roleId, {}, {
            headers : {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async deleteRoleVinculado( usuarioGrupoId : number, roleId : number, token : string ) {
        return await axios.delete( BASE_URL + "/usuario-grupos/"+usuarioGrupoId+"/roles/"+roleId, {
            headers : {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async deleteUsuarioGrupo( usuarioGrupoId : number, token : string ) {
        return await axios.delete( BASE_URL + "/usuario-grupos/"+usuarioGrupoId, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

}