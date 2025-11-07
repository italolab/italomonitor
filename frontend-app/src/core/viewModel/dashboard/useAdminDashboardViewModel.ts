import { useContext, useState } from "react";
import type { ConfigResponse } from "../../model/dto/response/ConfigResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { ConfigModel } from "../../model/ConfigModel";
import { AuthContext } from "../../../context/AuthProvider";

function useAdminDashboardViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [config, setConfig] = useState<ConfigResponse>( {
        id: 0,
        numPacotesPorLote: 0,
        monitoramentoDelay: 0,
        registroEventoPeriodo: 0,
        numThreadsLimite: 0,
        monitorServers: []
    } );

    const {setAccessToken} = useContext(AuthContext);

    const configModel = new ConfigModel( setAccessToken );

    const load = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            const response = await configModel.getConfig();

            setConfig( response.data );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false )
            throw error;
        }
    };

    return {
        load,
        config,
        errorMessage,
        infoMessage,
        loading
    };
}

export default useAdminDashboardViewModel;