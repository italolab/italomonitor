import { noInterceptorAPI } from "./Api";
import type { LoginRequest } from "../model/dto/request/LoginRequest";

export class AuthModel {

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