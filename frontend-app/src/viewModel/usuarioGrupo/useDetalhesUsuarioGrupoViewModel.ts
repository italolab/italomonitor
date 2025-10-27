import { useContext, useState } from "react";
import { type UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { UsuarioGrupoModel } from "../../model/UsuarioGrupoModel";
import { AuthContext } from "../../context/AuthProvider";

function useDetalhesUsuarioGrupoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuarioGrupo, setUsuarioGrupo] = useState<UsuarioGrupoResponse>( {
        id: 0,
        nome: '',
    } );
    
    const {setAccessToken} = useContext(AuthContext);

    const usuarioGrupoModel = new UsuarioGrupoModel( setAccessToken );

    const loadUsuarioGrupo = async ( usuarioGrupoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

        try {
            const response = await usuarioGrupoModel.getUsuarioGrupo( usuarioGrupoId );

            setUsuarioGrupo( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadUsuarioGrupo, usuarioGrupo, loading, errorMessage, infoMessage };
}

export default useDetalhesUsuarioGrupoViewModel;