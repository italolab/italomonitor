import axios from "axios";
import { BASE_API_URL } from "../constants/api-constants";
import type { SaveEmpresaRequest } from "./dto/request/SaveEmpresaRequest";

export class EmpresaModel {

    async createEmpresa( empresaSave : SaveEmpresaRequest ) {
        return await axios.post( BASE_API_URL + "/empresas", empresaSave, {
            withCredentials: true
        } );
    }

    async updateEmpresa( empresaId : number, empresaSave : SaveEmpresaRequest ) {
        return await axios.put( BASE_API_URL + "/empresas/"+empresaId, empresaSave, {
            withCredentials: true
        } );
    } 

    async filterEmpresas( nomepart : string ) {
        return await axios.get( BASE_API_URL + "/empresas?nomepart="+nomepart, {
            withCredentials: true
        } );
    }

    async getEmpresa( empresaId : number ) {
        return await axios.get( BASE_API_URL + "/empresas/"+empresaId+"/get", {
            withCredentials: true
        })
    }

    async deleteEmpresa( empresaId : number ) {
        return await axios.delete( BASE_API_URL + "/empresas/"+empresaId, {
            withCredentials: true
        } );
    }

}