import { useContext, useState } from "react";
import { UsuarioGrupoModel } from "../../model/UsuarioGrupoModel";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { AuthContext } from "../../context/AuthProvider";
import { type UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";

function useManterUsuarioGrupoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuarioGrupos, setUsuarioGrupos] = useState<UsuarioGrupoResponse[]>( [] );

    const [nomePart, setNomePart] = useState<string>( '' );

    const {token} = useContext(AuthContext);

    const usuarioGrupoModel = new UsuarioGrupoModel();

    const filterUsuarioGrupos = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await usuarioGrupoModel.filterUsuarioGrupos( nomePart, token );

            setUsuarioGrupos( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeUsuarioGrupo = async ( usuarioGrupoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await usuarioGrupoModel.deleteUsuarioGrupo( usuarioGrupoId, token );
            const response = await usuarioGrupoModel.filterUsuarioGrupos( nomePart, token );

            setUsuarioGrupos( response.data );
            setInfoMessage( 'Grupo de usuário deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getUsuarioGrupoById = ( usuarioGrupoId : number ) : UsuarioGrupoResponse | null => {
        for( let i = 0; i < usuarioGrupos.length; i++ )
            if ( usuarioGrupos[ i ].id === usuarioGrupoId )
                return usuarioGrupos[ i ];
        return null;
    };

    return { 
        filterUsuarioGrupos, 
        removeUsuarioGrupo,         
        getUsuarioGrupoById,
        usuarioGrupos, 
        nomePart,        
        loading, 
        errorMessage,
        infoMessage,
        setNomePart
    };
}

export default useManterUsuarioGrupoViewModel;