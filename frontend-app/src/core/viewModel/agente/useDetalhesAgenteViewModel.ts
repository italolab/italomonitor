import { useContext, useState } from "react";
import { DEFAULT_AGENTE_OBJ, type AgenteResponse } from "../../model/dto/response/AgenteResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { AgenteModel } from "../../model/AgenteModel";
import { AuthContext } from "../../../context/AuthProvider";


function useDetalhesAgenteViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [agente, setAgente] = useState<AgenteResponse>( DEFAULT_AGENTE_OBJ );
    
    const {setAccessToken} = useContext(AuthContext);

    const agenteModel = new AgenteModel( setAccessToken );

    const loadAgente = async ( agenteId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await agenteModel.getAgente( agenteId );

            setAgente( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadAgente, agente, loading, errorMessage, infoMessage };
}

export default useDetalhesAgenteViewModel;