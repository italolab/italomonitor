import { useContext, useState } from "react";
import { type UsuarioResponse } from "../../model/dto/response/UsuarioResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { UsuarioModel } from "../../model/UsuarioModel";
import { AuthContext } from "../../../context/AuthProvider";

function useDetalhesUsuarioViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuario, setUsuario] = useState<UsuarioResponse>( {
        id: 0,
        nome: '',
        email: '',
        username: '',
        empresa: {
            id: 0,
            nome: '',
            emailNotif: '',
            porcentagemMaxFalhasPorLote: 0
        },
        grupos: []
    } );
    
    const {setAccessToken} = useContext(AuthContext);

    const usuarioModel = new UsuarioModel( setAccessToken );

    const loadUsuario = async ( usuarioId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

        try {
            const response = await usuarioModel.getUsuario( usuarioId );

            setUsuario( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadUsuario, usuario, loading, errorMessage, infoMessage };
}

export default useDetalhesUsuarioViewModel;