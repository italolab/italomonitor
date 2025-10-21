import axios from "axios";
import type { SaveDispositivoRequest } from "./dto/request/SaveDispositivoRequest";
import { BASE_URL } from "../constants/api-constants";

export class DispositivoModel {

    async createDispositivo( dispositivoSave : SaveDispositivoRequest, token : string ) {
        return await axios.post( BASE_URL + "/dispositivos", dispositivoSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    }

    async updateDispositivo( dispositivoId : number, dispositivoSave : SaveDispositivoRequest, token : string ) {
        return await axios.put( BASE_URL + "/dispositivos/"+dispositivoId, dispositivoSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    } 

    async filterDispositivos( hostpart : string, nomepart : string, localpart : string, token : string ) {
        return await axios.get( BASE_URL + "/dispositivos?hostpart="+hostpart+"&nomepart="+nomepart+"&localpart="+localpart, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async getDispositivo( dispositivoId : number, token : string ) {
        return await axios.get( BASE_URL + "/dispositivos/"+dispositivoId+"/get", {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        })
    }

    async deleteDispositivo( dispositivoId : number, token : string ) {
        return await axios.delete( BASE_URL + "/dispositivos/"+dispositivoId, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

}