import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";

export class DispositivoMonitorModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async startMonitoramento( dispositivoId : number ) {
        return api.post( "/monitoramento/dispositivos/"+dispositivoId+"/start", {} );
    }

    async stopMonitoramento( dispositivoId : number ) {
        return api.post( "/monitoramento/dispositivos/"+dispositivoId+"/stop", {} );
    }

}