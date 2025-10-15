import { useContext, useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import { AuthContext } from "../../context/AuthProvider";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";
import type { UsuarioResponse } from "../../model/dto/response/UsuarioResponse";

function useVincularUsuarioGrupoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuario, setUsuario] = useState<UsuarioResponse>( {
        id: 0,
        nome: '',
        email: '',
        username: ''
    } );

    const [grupos, setGrupos] = useState<UsuarioGrupoResponse[]>( [] );

    const usuarioModel = new UsuarioModel();

    const {token} = useContext(AuthContext);

    const loadUsuario = async ( usuarioId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            const usuarioResponse = await usuarioModel.getUsuario( usuarioId, token );
            const gruposResponse = await usuarioModel.getGrupos( usuarioId, token );

            setUsuario( usuarioResponse.data );
            setGrupos( gruposResponse.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadUsuario, usuario, grupos, loading, errorMessage, infoMessage };
}

export default useVincularUsuarioGrupoViewModel;