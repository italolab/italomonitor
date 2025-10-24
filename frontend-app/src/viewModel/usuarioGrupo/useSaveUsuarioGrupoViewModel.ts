import { useContext, useState } from "react";
import { UsuarioGrupoModel } from "../../model/UsuarioGrupoModel";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";
import type { SaveUsuarioGrupoRequest } from "../../model/dto/request/SaveUsuarioGrupoRequest";
import { AuthContext } from "../../context/AuthProvider";


function useSaveUsuarioGrupoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );
    
    const {setAccessToken} = useContext(AuthContext);

    const usuarioGrupoModel = new UsuarioGrupoModel( setAccessToken );

    const createUsuarioGrupo = async ( grupo : SaveUsuarioGrupoRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await usuarioGrupoModel.createUsuarioGrupo( grupo );

            setInfoMessage( 'Grupo de usuário registrado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateUsuarioGrupo = async ( usuarioGrupoId : number, grupo : SaveUsuarioGrupoRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await usuarioGrupoModel.updateUsuarioGrupo( usuarioGrupoId, grupo );

            setInfoMessage( 'Grupo de usuário alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getUsuarioGrupo = async ( usuarioGrupoId : number ) : Promise<UsuarioGrupoResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await usuarioGrupoModel.getUsuarioGrupo( usuarioGrupoId );
            
            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        createUsuarioGrupo, 
        updateUsuarioGrupo, 
        getUsuarioGrupo, 
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveUsuarioGrupoViewModel;