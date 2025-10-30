import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveConfigRequest } from "../model/dto/request/SaveConfigRequest";

export class ConfigModel {
    
    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async updateConfig( config : SaveConfigRequest ) {
        return await api.post( "/config", config );
    }

    async getConfig() {
        return await api.get( "/config" );
    }

}