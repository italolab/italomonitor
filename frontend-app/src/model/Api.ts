import axios, { AxiosError, type AxiosInstance } from "axios";
import { BASE_API_URL } from "../constants/api-constants";

export type SetAccessTokenFunction = ( t : string ) => void;

export const api : AxiosInstance = axios.create( {
    baseURL: BASE_API_URL,
    withCredentials: true
} );

export function configuraInterceptor( setAccessToken : SetAccessTokenFunction ) {
    api.interceptors.response.use( 
        (response) => response,
        async ( error : AxiosError ) => {
            if ( error.response?.status === 401 ) {
                try {
                    const response = await api.post( '/auth/refresh-token' );

                    setAccessToken( response.data.accessToken )
                } catch ( refreshError ) {
                    console.error( refreshError );
                }
            }

            return Promise.reject( error );
        }
    );
}