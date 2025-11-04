import { useContext, useState } from "react";
import { AuthModel } from "../model/AuthModel";
import type { LoginRequest } from "../model/dto/request/LoginRequest";
import { extractErrorMessage } from "../util/sistema-util";
import { AuthContext } from "../../context/AuthProvider";
import type { AxiosResponse } from "axios";
import type { LoginResponse } from "../model/dto/response/LoginResponse";

export function useLoginViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const {setAccessToken} = useContext(AuthContext);

    const authModel = new AuthModel();

    const logon = async ( loginReq: LoginRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response : AxiosResponse<LoginResponse> = await authModel.login( loginReq );

            setAccessToken( response.data.accessToken );

            localStorage.setItem( 'username', response.data.username );
            localStorage.setItem( 'nome', response.data.nome );
            localStorage.setItem( 'empresaId', ''+response.data.empresaId );
            localStorage.setItem( 'perfil', response.data.perfil );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { logon, errorMessage, infoMessage, loading }
}

