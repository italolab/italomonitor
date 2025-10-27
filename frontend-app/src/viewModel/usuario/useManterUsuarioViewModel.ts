import { useContext, useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type UsuarioResponse } from "../../model/dto/response/UsuarioResponse";
import { AuthContext } from "../../context/AuthProvider";

function useManterUsuarioViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuarios, setUsuarios] = useState<UsuarioResponse[]>( [] );

    const [nomePart, setNomePart] = useState<string>( '' );
    
    const {setAccessToken} = useContext(AuthContext);

    const usuarioModel = new UsuarioModel( setAccessToken );

    const filterUsuarios = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await usuarioModel.filterUsuarios( nomePart );

            setUsuarios( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeUsuario = async ( usuarioId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await usuarioModel.deleteUsuario( usuarioId );
            const response = await usuarioModel.filterUsuarios( nomePart );

            setUsuarios( response.data );
            setInfoMessage( 'Usuário deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getUsuarioById = ( usuarioId : number ) : UsuarioResponse | null => {
        for( let i = 0; i < usuarios.length; i++ )
            if ( usuarios[ i ].id === usuarioId )
                return usuarios[ i ];
        return null;
    };

    return { 
        filterUsuarios, 
        removeUsuario,         
        getUsuarioById,
        usuarios, 
        nomePart,        
        loading, 
        errorMessage,
        infoMessage,
        setNomePart
    };
}

export default useManterUsuarioViewModel;