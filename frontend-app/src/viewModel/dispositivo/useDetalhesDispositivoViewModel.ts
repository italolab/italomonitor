import { useContext, useState } from "react";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { DispositivoModel } from "../../model/DispositivoModel";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";
import { AuthContext } from "../../context/AuthProvider";
import { Client } from "@stomp/stompjs";
import { BASE_WS_URL } from "../../constants/api-constants";

function useDetalhesDispositivoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivo, setDispositivo] = useState<DispositivoResponse>( {
        id: 0,
        host: '',
        nome: '',
        descricao: '',
        localizacao: '',
        sendoMonitorado: false,
        status : 'INATIVO',
        empresa: {
            id: 0,
            nome: '',
            emailNotif: '',
            porcentagemMaxFalhasPorLote: 0
        }
    } );

    const {accessToken, setAccessToken} = useContext(AuthContext);

    const dispositivoModel = new DispositivoModel( setAccessToken );
    const dispositivoMonitorModel = new DispositivoMonitorModel( setAccessToken );

    const websocketConnect = () : () => void => {
        const client = new Client( {
            brokerURL: BASE_WS_URL,
            connectHeaders: {
                Authorization: `Bearer ${accessToken}`
            },
            reconnectDelay: 5000,
            onConnect: () => {
                client.subscribe(`/user/topic/dispositivo`, (message) => {
                    const data = JSON.parse( message.body );
                    setDispositivo( data ); 
                } );
            },
            onStompError: ( frame ) => {
                console.error( frame.body );
            }         
        } );

        client.activate();

        return () => {
            client.deactivate();
        };
    };

    const loadDispositivo = async ( dispositivoId : number ) => {
        setInfoMessage( null );
        setLoading( false );

        try {
            const response = await dispositivoModel.getDispositivo( dispositivoId );

            setDispositivo( response.data );
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
        setLoading( false );

        try {
            await dispositivoMonitorModel.startMonitoramento( dispositivoId );
            dispositivo.sendoMonitorado = true;

            setInfoMessage( 'Dispositivo sendo monitorado!' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const stopMonitoramento = async ( dispositivoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

        try {
            await dispositivoMonitorModel.stopMonitoramento( dispositivoId );
            dispositivo.sendoMonitorado = false;

            setInfoMessage( 'Dispositivo não mais monitorado!' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { 
        loadDispositivo, 
        startMonitoramento, 
        stopMonitoramento, 
        websocketConnect,
        dispositivo, 
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage
    };
}

export default useDetalhesDispositivoViewModel;