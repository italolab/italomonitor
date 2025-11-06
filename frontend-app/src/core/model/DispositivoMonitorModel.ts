import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";

export class DispositivoMonitorModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async startAllMonitoramentos() {
        return await api.post( "/monitoramento/dispositivos/start-all-monitoramentos", {} );
    }

    async startMonitoramento( dispositivoId : number ) {
        return await api.post( "/monitoramento/dispositivos/"+dispositivoId+"/start", {} );
    }

    async stopMonitoramento( dispositivoId : number ) {
        return await api.post( "/monitoramento/dispositivos/"+dispositivoId+"/stop", {} );
    }

    async startEmpresaMonitoramentos( empresaId : number ) {
        return await api.post( "/monitoramento/dispositivos/empresa/"+empresaId+"/start-all", {} );
    }

    async stopEmpresaMonitoramentos( empresaId : number ) {
        return await api.post( "/monitoramento/dispositivos/empresa/"+empresaId+"/stop-all", {} );
    }

}