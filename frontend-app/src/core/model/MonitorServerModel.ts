import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveMonitorServerRequest } from "./dto/request/SaveMonitorServerRequest";

export class MonitorServerModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createMonitorServer( monitorServerSave : SaveMonitorServerRequest ) {
        return await api.post( "/monitor-servers", monitorServerSave );
    }

    async updateMonitorServer( monitorServerId : number, monitorServerSave : SaveMonitorServerRequest ) {
        return await api.put( "/monitor-servers/"+monitorServerId, monitorServerSave );
    } 

    async filterMonitorServers( hostpart : string ) {
        return await api.get( "/monitor-servers?hostpart="+hostpart );
    }

    async getMonitorServer( MonitorServerId : number ) {
        return await api.get( "/monitor-servers/"+MonitorServerId+"/get" );
    }

    async deleteMonitorServer( MonitorServerId : number ) {
        return await api.delete( "/monitor-servers/"+MonitorServerId );
    }

}