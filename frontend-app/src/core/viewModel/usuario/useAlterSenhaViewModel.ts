import { useContext, useState } from "react";
import { AuthContext } from "../../../context/AuthProvider";
import { UsuarioModel } from "../../model/UsuarioModel";
import type { AlterSenhaRequest } from "../../model/dto/request/AlterSenhaRequest";
import { extractErrorMessage } from "../../util/sistema-util";
import type { UsuarioResponse } from "../../model/dto/response/UsuarioResponse";

function useAlterSenhaViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuario, setUsuario] = useState<UsuarioResponse>( {
        id: 0,
        nome: '',
        email: '',
        username: '',
        perfil: 'ADMIN',
        empresa: {
            id: 0,
            nome: '',
            emailNotif: '',
            telegramChatId: '',
            porcentagemMaxFalhasPorLote: 0,
            maxDispositivosQuant: 0,
            minTempoParaProxNotif: 0,
            diaPagto: 0,
            temporario: false,
            usoTemporarioPor: 0,
            bloqueada: false,
            criadoEm: new Date()
        },
        grupos: []
    } );

    const { setAccessToken } = useContext( AuthContext );

    const usuarioModel = new UsuarioModel( setAccessToken );

    const load = async ( usuarioId : number ) => {
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

    const alterSenha = async ( usuarioId : number, alterSenhaSave : AlterSenhaRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await usuarioModel.alterSenha( usuarioId, alterSenhaSave );

            setInfoMessage( "Senha alterada com sucesso." );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error; 
        }
    };

    return {
        load,
        alterSenha,
        usuario,
        infoMessage,
        errorMessage,
        loading,
        setErrorMessage
    };
}

export default useAlterSenhaViewModel;