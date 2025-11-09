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
        perfil: 'USUARIO',
        empresa: {
            id: 0,
            nome: '',
            emailNotif: '',
            telegramChatId: '',
            porcentagemMaxFalhasPorLote: 0,
            maxDispositivosQuant: 0,
            minTempoParaProximoEvento: 0,
            diaPagto: 0,
            temporario: false,
            usoTemporarioPor: 0,
            criadoEm: new Date()
        },
        grupos: []
    } );
    
    const {setAccessToken} = useContext(AuthContext);

    const usuarioModel = new UsuarioModel( setAccessToken );

    const loadUsuario = async ( usuarioId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

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