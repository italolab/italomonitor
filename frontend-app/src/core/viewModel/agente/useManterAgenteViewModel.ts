import { useContext, useState } from "react";
import { AgenteModel } from "../../model/AgenteModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type AgenteResponse } from "../../model/dto/response/AgenteResponse";
import { AuthContext } from "../../../context/AuthProvider";

function useManterAgenteViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [agentes, setAgentes] = useState<AgenteResponse[]>( [] );

    const [nomePart, setNomePart] = useState<string>( '' );
    
    const {setAccessToken} = useContext(AuthContext);

    const agenteModel = new AgenteModel( setAccessToken );

    const filterAgentes = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await agenteModel.filterAgentes( nomePart, empresaId );

            if ( response.data.length === 0 )
                setInfoMessage( 'Nenhum agente encontrado pelos critérios de busca informados.' );

            setAgentes( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeAgente = async ( agenteId : number, empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await agenteModel.deleteAgente( agenteId );
            const response = await agenteModel.filterAgentes( nomePart, empresaId );

            setAgentes( response.data );
            setInfoMessage( 'Agente deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getAgenteById = ( agenteId : number ) : AgenteResponse | null => {
        for( let i = 0; i < agentes.length; i++ )
            if ( agentes[ i ].id === agenteId )
                return agentes[ i ];
        return null;
    };

    return { 
        filterAgentes, 
        removeAgente,         
        getAgenteById,
        agentes, 
        nomePart,        
        loading, 
        errorMessage,
        infoMessage,
        setNomePart
    };
}

export default useManterAgenteViewModel;