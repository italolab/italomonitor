import { useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import type { CreateUsuarioRequest } from "../../model/dto/request/CreateUsuarioRequest";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { UsuarioResponse } from "../../model/dto/response/UsuarioResponse";
import type { UpdateUsuarioRequest } from "../../model/dto/request/UpdateUsuarioRequest";
import { EmpresaModel } from "../../model/EmpresaModel";


function useSaveUsuarioViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const usuarioModel = new UsuarioModel();
    const empresaModel = new EmpresaModel();

    const createUsuario = async ( usuario : CreateUsuarioRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await usuarioModel.createUsuario( usuario );

            setInfoMessage( 'Usuário registrado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateUsuario = async ( usuarioId : number, usuario : UpdateUsuarioRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await usuarioModel.updateUsuario( usuarioId, usuario );

            setInfoMessage( 'Usuário alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getUsuario = async ( usuarioId : number ) : Promise<UsuarioResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await usuarioModel.getUsuario( usuarioId );

            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const getEmpresas = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await empresaModel.filterEmpresas( "" );

            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { 
        createUsuario, 
        updateUsuario, 
        getUsuario, 
        getEmpresas,
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveUsuarioViewModel;