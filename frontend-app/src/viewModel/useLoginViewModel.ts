import { useContext, useState } from "react";
import { AuthModel } from "../model/AuthModel";
import type { LoginResponse } from "../model/dto/response/LoginResponse";
import type { LoginRequest } from "../model/dto/request/LoginRequest";
import { extractErrorMessage } from "../util/SistemaUtil";
import { AuthContext } from "../context/AuthProvider";

export function useLoginViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [loginData, setLoginData] = useState<LoginResponse|null>( null );

    const {setNome, setUsername, setAccessToken} = useContext(AuthContext);

    const authModel = new AuthModel();

    const logon = async ( loginReq: LoginRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await authModel.login( loginReq );
            setNome( response.data.nome );
            setUsername( response.data.username );
            setAccessToken( response.data.accessToken );

            setLoginData( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { logon, loginData, errorMessage, infoMessage, loading }
}

