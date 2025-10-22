import axios from "axios";
import { BASE_URL } from "../constants/api-constants";

export class DispositivoMonitorModel {

    async startMonitoramento( dispositivoId : number ) {
        return axios.post( BASE_URL + "/dispositivos/monitoramento/start/"+dispositivoId, {}, {
            withCredentials: true
        } );
    }

    async stopMonitoramento( dispositivoId : number ) {
        return axios.post( BASE_URL + "/dispositivos/monitoramento/stop/"+dispositivoId, {}, {
            withCredentials: true
        } );
    }

}