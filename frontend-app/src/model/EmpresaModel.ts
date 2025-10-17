import axios from "axios";
import { BASE_URL } from "../constants/api-constants";
import type { SaveEmpresaRequest } from "./dto/request/SaveEmpresaRequest";

export class EmpresaModel {

    async createEmpresa( empresaSave : SaveEmpresaRequest, token : string ) {
        return await axios.post( BASE_URL + "/empresas", empresaSave, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async updateEmpresa( empresaId : number, empresaSave : SaveEmpresaRequest, token : string ) {
        return await axios.put( BASE_URL + "/empresas/"+empresaId, empresaSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    } 

    async filterEmpresas( nomepart : string, token : string ) {
        return await axios.get( BASE_URL + "/empresas?nomepart="+nomepart, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async getEmpresa( empresaId : number, token : string ) {
        return await axios.get( BASE_URL + "/empresas/"+empresaId+"/get", {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        })
    }

    async deleteEmpresa( empresaId : number, token : string ) {
        return await axios.delete( BASE_URL + "/empresas/"+empresaId, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

}