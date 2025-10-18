import { useContext, useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import { AuthContext } from "../../context/AuthProvider";
import type { CreateUsuarioRequest } from "../../model/dto/request/CreateUsuarioRequest";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { UsuarioResponse } from "../../model/dto/response/UsuarioResponse";
import type { UpdateUsuarioRequest } from "../../model/dto/request/UpdateUsuarioRequest";
import { EmpresaModel } from "../../model/EmpresaModel";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";


function useSaveUsuarioViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [empresas, setEmpresas] = useState<EmpresaResponse[]>( [] );

    const usuarioModel = new UsuarioModel();
    const empresaModel = new EmpresaModel();

    const {token} = useContext(AuthContext);

    const createUsuario = async ( usuario : CreateUsuarioRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await usuarioModel.createUsuario( usuario, token );

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
            await usuarioModel.updateUsuario( usuarioId, usuario, token );

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
            const response = await usuarioModel.getUsuario( usuarioId, token );

            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const loadEmpresas = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await empresaModel.filterEmpresas( "", token );

            setLoading( false );
            setEmpresas( response.data );
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
        loadEmpresas,
        empresas,
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveUsuarioViewModel;