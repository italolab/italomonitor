import { useContext, useState } from "react";
import { AgenteModel } from "../../model/AgenteModel";
import { extractErrorMessage } from "../../util/sistema-util";
import type { AgenteResponse } from "../../model/dto/response/AgenteResponse";
import type { SaveAgenteRequest } from "../../model/dto/request/SaveAgenteRequest";
import { AuthContext } from "../../../context/AuthProvider";


function useSaveAgenteViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );
    
    const {setAccessToken} = useContext(AuthContext);

    const agenteModel = new AgenteModel( setAccessToken );

    const createAgente = async ( agente : SaveAgenteRequest, empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            const response = await agenteModel.createAgente( agente, empresaId );

            setInfoMessage( 'Agente registrado com sucesso.' );
            setLoading( false );

            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateAgente = async ( agenteId : number, agente : SaveAgenteRequest, empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await agenteModel.updateAgente( agenteId, agente, empresaId );

            setInfoMessage( 'Agente alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getAgente = async ( agenteId : number ) : Promise<AgenteResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await agenteModel.getAgente( agenteId );
            
            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const copyChaveToClipboard = async ( chave : string) => {
        navigator.clipboard.writeText( chave ).then( () => {
            setInfoMessage( "Copiado com sucesso." );
        } ).catch( e => {
            setErrorMessage( "Não foi possível copiar os dados do pix." );
            throw e;
        } );
    };

    return { 
        createAgente, 
        updateAgente, 
        getAgente, 
        copyChaveToClipboard,
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveAgenteViewModel;