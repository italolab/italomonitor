import axios from "axios";
import type { SaveDispositivoRequest } from "./dto/request/SaveDispositivoRequest";
import { BASE_URL } from "../constants/api-constants";

export class DispositivoModel {

    async createDispositivo( dispositivoSave : SaveDispositivoRequest ) {
        return await axios.post( BASE_URL + "/dispositivos", dispositivoSave, {
            withCredentials: true
        } );
    }

    async updateDispositivo( dispositivoId : number, dispositivoSave : SaveDispositivoRequest ) {
        return await axios.put( BASE_URL + "/dispositivos/"+dispositivoId, dispositivoSave, {
            withCredentials: true
        } );
    } 

    async filterDispositivos( hostpart : string, nomepart : string, localpart : string ) {
        return await axios.get( BASE_URL + "/dispositivos?hostpart="+hostpart+"&nomepart="+nomepart+"&localpart="+localpart, {
            withCredentials: true
        } );
    }

    async getDispositivo( dispositivoId : number ) {
        return await axios.get( BASE_URL + "/dispositivos/"+dispositivoId+"/get", {
            withCredentials: true
        })
    }

    async deleteDispositivo( dispositivoId : number ) {
        return await axios.delete( BASE_URL + "/dispositivos/"+dispositivoId, {
            withCredentials: true
        } );
    }

}