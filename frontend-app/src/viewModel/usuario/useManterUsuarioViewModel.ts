import { useContext, useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { AuthContext } from "../../context/AuthProvider";
import { type UsuarioResponse } from "../../model/dto/response/UsuarioResponse";

function useManterUsuarioViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuarios, setUsuarios] = useState<UsuarioResponse[]>( [] );

    const {token} = useContext(AuthContext);

    const usuarioModel = new UsuarioModel();

    const filterUsuarios = async ( nomepart : string ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await usuarioModel.filterUsuarios( nomepart, token );

            setUsuarios( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { filterUsuarios, usuarios, loading, errorMessage, infoMessage };
}

export default useManterUsuarioViewModel;