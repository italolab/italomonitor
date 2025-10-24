import axios, { AxiosError, type AxiosInstance, type AxiosRequestConfig, type InternalAxiosRequestConfig } from "axios";
import { BASE_API_URL } from "../constants/api-constants";

export type SetAccessTokenFunction = ( t : string ) => void;

interface ErrorConfig extends InternalAxiosRequestConfig<unknown> {
    _retry? : boolean;
}

export const api : AxiosInstance = axios.create( {
    baseURL: BASE_API_URL,
    withCredentials: true
} );

export const refreshTokenAPI : AxiosInstance = axios.create( { 
    baseURL: BASE_API_URL,
    withCredentials: true
} );

export function configuraInterceptor( setAccessToken : SetAccessTokenFunction ) {
    api.interceptors.response.use( 
        (response) => response,
        async ( error : AxiosError ) => {            
            if ( ( error.response?.status === 401 || error.response?.status == 403 ) && !error.config!._retry ) {
                error.config!._retry = true;
                try {                    
                    const response = await refreshTokenAPI.post( '/auth/refresh-token' );

                    setAccessToken( response.data.accessToken );
                    return api( error.config! );
                } catch ( refreshError ) {
                    console.error( refreshError );
                }
            }

            return Promise.reject( error );
        }
    );
}