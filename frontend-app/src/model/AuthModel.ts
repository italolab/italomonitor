import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { LoginRequest } from "./dto/request/LoginRequest";

export class AuthModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async login( loginObj : LoginRequest ) {
        return await api.post( "/auth/login", loginObj );
    }

    async logout() {
        return await api.post( "/auth/logout", {} );
    }

}