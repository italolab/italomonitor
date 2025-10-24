import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";

export class DispositivoMonitorModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async startMonitoramento( dispositivoId : number ) {
        return api.post( "/dispositivos/monitoramento/start/"+dispositivoId, {} );
    }

    async stopMonitoramento( dispositivoId : number ) {
        return api.post( "/dispositivos/monitoramento/stop/"+dispositivoId, {} );
    }

}