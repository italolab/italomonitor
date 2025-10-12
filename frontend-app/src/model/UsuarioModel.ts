import axios from "axios";
import { BASE_URL } from "../constants/api-constants";
import type { CreateUsuarioRequest } from "./dto/request/CreateUsuarioRequest";
import type { UpdateUsuarioRequest } from "./dto/request/UpdateUsuarioRequest";


export class UsuarioModel {

    async createUsuario( usuarioSave : CreateUsuarioRequest, token : string ) {
        return await axios.post( BASE_URL + "/usuarios", usuarioSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    }

    async updateUsuario( usuarioId : number, usuarioSave : UpdateUsuarioRequest, token : string ) {
        return await axios.put( BASE_URL + "/usuarios/"+usuarioId, usuarioSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    } 

    async filterUsuarios( nomepart : string, token : string ) {
        return await axios.get( BASE_URL + "/usuarios?nomepart="+nomepart, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async getUsuario( usuarioId : number, token : string ) {
        return await axios.get( BASE_URL + "/usuarios/get/"+usuarioId, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        })
    }

    async deleteUsuario( usuarioId : number, token : string ) {
        return await axios.delete( BASE_URL + "/usuarios/"+usuarioId, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

}