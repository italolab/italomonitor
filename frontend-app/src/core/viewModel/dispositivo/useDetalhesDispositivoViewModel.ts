import { useContext, useRef, useState } from "react";
import { DEFAULT_DISPOSITIVO_OBJ, type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { DispositivoModel } from "../../model/DispositivoModel";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";
import { AuthContext } from "../../../context/AuthProvider";
import useWebsocket from "../useWebsocket";
import { MENSAGEM_DELAY } from "../../constants/constants";
import type { Client } from "@stomp/stompjs";
import { BASE_WS_URL, DISPOSITIVOS_TOPIC } from "../../constants/websocket-constants";

function useDetalhesDispositivoViewModel() {
    
    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivo, setDispositivo] = useState<DispositivoResponse>( DEFAULT_DISPOSITIVO_OBJ );

    const dispositivoIDRef = useRef( 0 );

    const {setAccessToken} = useContext(AuthContext);

    const dispositivoModel = new DispositivoModel( setAccessToken );
    const dispositivoMonitorModel = new DispositivoMonitorModel( setAccessToken );

    const wsRefresh = useWebsocket();

    const websocketConnect = async () => {
        return wsRefresh.connect( BASE_WS_URL, onWSConnect, setErrorMessage );
    };

    const onWSConnect = async ( client : Client ) => {
        client.subscribe( DISPOSITIVOS_TOPIC, (message) => {
            const disp : DispositivoResponse = JSON.parse( message.body );
            if ( dispositivoIDRef.current == disp.id )
                setDispositivo( disp );
        } );
    }

    const loadDispositivo = async ( dispositivoId : number ) => {
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await dispositivoModel.getDispositivo( dispositivoId );

            dispositivoIDRef.current = response.data.id;

            setDispositivo( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );            
            setTimeout( () => setErrorMessage( null ), MENSAGEM_DELAY );
            setLoading( false );
            throw error;
        }
    };

    const removeDispositivo = async ( dispositivoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await dispositivoModel.deleteDispositivo( dispositivoId );
            
            setInfoMessage( 'Dispositivo deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };
    
    const startMonitoramento = async ( dispositivoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await dispositivoMonitorModel.startMonitoramento( dispositivoId );
            dispositivo.sendoMonitorado = true;

            setInfoMessage( 'Dispositivo sendo monitorado!' );
            setTimeout( () => setInfoMessage( null ), MENSAGEM_DELAY );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );            
            setTimeout( () => setErrorMessage( null ), MENSAGEM_DELAY );
            setLoading( false );
            throw error;
        }
    };

    const stopMonitoramento = async ( dispositivoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await dispositivoMonitorModel.stopMonitoramento( dispositivoId );
            dispositivo.sendoMonitorado = false;

            setInfoMessage( 'Dispositivo não mais monitorado!' );
            setTimeout( () => setInfoMessage( null ), MENSAGEM_DELAY );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );            
            setTimeout( () => setErrorMessage( null ), MENSAGEM_DELAY );
            setLoading( false );
            throw error;
        }
    };

    return { 
        loadDispositivo, 
        removeDispositivo,
        startMonitoramento, 
        stopMonitoramento, 
        websocketConnect,
        dispositivo, 
        loading, 
        errorMessage, 
        infoMessage
    };
}

export default useDetalhesDispositivoViewModel;