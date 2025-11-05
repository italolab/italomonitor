import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";

export class DispositivoMonitorModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async startOrRestartMonitoramentos() {
        return await api.post( "/monitoramento/dispositivos/start-or-restart-monitoramentos", {} );
    }

    async startMonitoramento( dispositivoId : number ) {
        return await api.post( "/monitoramento/dispositivos/"+dispositivoId+"/start", {} );
    }

    async stopMonitoramento( dispositivoId : number ) {
        return await api.post( "/monitoramento/dispositivos/"+dispositivoId+"/stop", {} );
    }

    async startAllMonitoramentos( empresaId : number ) {
        return await api.post( "/monitoramento/dispositivos/empresa/"+empresaId+"/start-all", {} );
    }

    async stopAllMonitoramentos( empresaId : number ) {
        return await api.post( "/monitoramento/dispositivos/empresa/"+empresaId+"/stop-all", {} );
    }

}