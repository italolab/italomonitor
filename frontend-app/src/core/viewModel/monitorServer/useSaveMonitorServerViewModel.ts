import { useContext, useState } from "react";
import { MonitorServerModel } from "../../model/MonitorServerModel";
import { extractErrorMessage } from "../../util/sistema-util";
import type { MonitorServerResponse } from "../../model/dto/response/MonitorServerResponse";
import type { SaveMonitorServerRequest } from "../../model/dto/request/SaveMonitorServerRequest";
import { AuthContext } from "../../../context/AuthProvider";


function useSaveMonitorServerViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );
    
    const {setAccessToken} = useContext(AuthContext);

    const monitorServerModel = new MonitorServerModel( setAccessToken );

    const createMonitorServer = async ( monitorServer : SaveMonitorServerRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await monitorServerModel.createMonitorServer( monitorServer );

            setInfoMessage( 'Servidor de monitoramento registrado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateMonitorServer = async ( monitorServerId : number, monitorServer : SaveMonitorServerRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await monitorServerModel.updateMonitorServer( monitorServerId, monitorServer );

            setInfoMessage( 'Servidor de monitoramento alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getMonitorServer = async ( monitorServerId : number ) : Promise<MonitorServerResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await monitorServerModel.getMonitorServer( monitorServerId );
            
            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        createMonitorServer, 
        updateMonitorServer, 
        getMonitorServer, 
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveMonitorServerViewModel;