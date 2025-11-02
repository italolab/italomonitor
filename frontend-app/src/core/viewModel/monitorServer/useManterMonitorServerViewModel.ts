import { useContext, useState } from "react";
import { MonitorServerModel } from "../../model/MonitorServerModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type MonitorServerResponse } from "../../model/dto/response/MonitorServerResponse";
import { AuthContext } from "../../../context/AuthProvider";

function useManterMonitorServerViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [monitorServers, setMonitorServers] = useState<MonitorServerResponse[]>( [] );
    
    const {setAccessToken} = useContext(AuthContext);

    const monitorServerModel = new MonitorServerModel( setAccessToken );

    const loadMonitorServers = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await monitorServerModel.filterMonitorServers( '' );

            setMonitorServers( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeMonitorServer = async ( monitorServerId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await monitorServerModel.deleteMonitorServer( monitorServerId );
            const response = await monitorServerModel.filterMonitorServers( '' );

            setMonitorServers( response.data );
            setInfoMessage( 'Servidor de monitoramento deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getMonitorServerById = ( monitorServerId : number ) : MonitorServerResponse | null => {
        for( let i = 0; i < monitorServers.length; i++ )
            if ( monitorServers[ i ].id === monitorServerId )
                return monitorServers[ i ];
        return null;
    };

    return { 
        loadMonitorServers, 
        removeMonitorServer,         
        getMonitorServerById,
        monitorServers, 
        loading, 
        errorMessage,
        infoMessage
    };
}

export default useManterMonitorServerViewModel;