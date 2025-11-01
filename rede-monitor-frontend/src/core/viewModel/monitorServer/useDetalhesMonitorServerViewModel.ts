import { useContext, useState } from "react";
import { type MonitorServerResponse } from "../../model/dto/response/MonitorServerResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { MonitorServerModel } from "../../model/MonitorServerModel";
import { AuthContext } from "../../../context/AuthProvider";


function useDetalhesMonitorServerViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [monitorServer, setMonitorServer] = useState<MonitorServerResponse>( {
        id: 0,
        host: '',
    } );
    
    const {setAccessToken} = useContext(AuthContext);

    const monitorServerModel = new MonitorServerModel( setAccessToken );

    const loadMonitorServer = async ( monitorServerId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

        try {
            const response = await monitorServerModel.getMonitorServer( monitorServerId );

            setMonitorServer( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadMonitorServer, monitorServer, loading, errorMessage, infoMessage };
}

export default useDetalhesMonitorServerViewModel;