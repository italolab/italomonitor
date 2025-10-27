import { configuraInterceptor, noInterceptorAPI, type SetAccessTokenFunction } from "./Api";
import type { LoginRequest } from "./dto/request/LoginRequest";

export class AuthModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async login( loginObj : LoginRequest ) {
        return await noInterceptorAPI.post( "/auth/login", loginObj );
    }

    async logout() {
        return await noInterceptorAPI.post( "/auth/logout", {} );
    }

    async refreshAccessToken() {
        return await noInterceptorAPI.post( '/auth/refresh-token', {} );
    }

}