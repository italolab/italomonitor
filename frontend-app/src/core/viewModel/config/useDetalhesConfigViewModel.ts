import { useContext, useState } from "react";
import { AuthContext } from "../../../context/AuthProvider";
import { ConfigModel } from "../../model/ConfigModel";
import { extractErrorMessage } from "../../util/sistema-util";
import type { ConfigResponse } from "../../model/dto/response/ConfigResponse";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";

function useDetalhesConfigViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [config, setConfig] = useState<ConfigResponse>( {
        id: 0,
        numPacotesPorLote: 0,
        monitoramentoDelay: 0,
        registroEventoPeriodo: 0,
        numThreadsLimite: 0,
        telegramBotToken: '',
        monitorServers: []
    } );

    const { setAccessToken } = useContext(AuthContext);

    const configModel = new ConfigModel( setAccessToken );
    const dispositivoMonitorModel = new DispositivoMonitorModel( setAccessToken );

    const loadConfig = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try{
            const response = await configModel.getConfig();

            setConfig( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const startAllMonitoramentos = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try{
            const response = await dispositivoMonitorModel.startAllMonitoramentos();
            const configResp = await configModel.getConfig();

            setConfig( configResp.data );

            setInfoMessage( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const stopAllMonitoramentos = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try{
            const response = await dispositivoMonitorModel.stopAllMonitoramentos();
            const configResp = await configModel.getConfig();

            setConfig( configResp.data );

            setInfoMessage( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        loadConfig, 
        startAllMonitoramentos, 
        stopAllMonitoramentos,
        config, 
        errorMessage, 
        infoMessage, 
        loading 
    };
}

export default useDetalhesConfigViewModel;