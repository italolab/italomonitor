import { useContext, useRef, useState } from "react";
import { DispositivoModel } from "../../model/DispositivoModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { AuthContext } from "../../../context/AuthProvider";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";
import { DEFAULT_EMPRESA_OBJ, type EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { EmpresaModel } from "../../model/EmpresaModel";
import useWebsocket from "../useWebsocket";
import type { DispositivosInfosResponse } from "../../model/dto/response/DispositivosInfosResponse";
import type { Client } from "@stomp/stompjs";
import { BASE_WS_URL, DISPOSITIVOS_INFOS_TOPIC, DISPOSITIVOS_TOPIC } from "../../constants/websocket-constants";

function useShowDispositivosViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivos, setDispositivos] = useState<DispositivoResponse[]>( [] );
    const [dispositivosFiltrados, setDispositivosFiltrados] = useState<DispositivoResponse[]>( [] );

    const [empresa, setEmpresa] = useState<EmpresaResponse>( DEFAULT_EMPRESA_OBJ );

    const [dispositivosInfos, setDispositivosInfos] = useState<DispositivosInfosResponse>( {
        sendoMonitoradosQuant: 0,
        quantTotal: 0
    } );

    const dispositivosFiltradosRef = useRef<DispositivoResponse[]>( [] );

    const {setAccessToken} = useContext(AuthContext);
    
    const dispositivoModel = new DispositivoModel( setAccessToken );
    const dispositivoMonitorModel = new DispositivoMonitorModel( setAccessToken );
    const empresaModel = new EmpresaModel( setAccessToken );

    const websocket = useWebsocket();

    const webSocketsConnect = async () : Promise<() => void> => {
        return await websocket.connect( BASE_WS_URL, onWSConnect, setErrorMessage );
    };

    const onWSConnect = async ( client : Client ) => {
        client.subscribe( DISPOSITIVOS_TOPIC, ( message ) => {
            const disp : DispositivoResponse = JSON.parse( message.body );

            const dispsRef : DispositivoResponse[] = dispositivosFiltradosRef.current;

            const disps = [];
            for( let i = 0; i < dispsRef.length; i++ ) {                        
                disps[ i ] = dispsRef[ i ];    
                if ( disps[ i ].id === disp.id ) {
                    disps[ i ] = disp;            
                }        
            }

            dispositivosFiltradosRef.current = disps;
            setDispositivosFiltrados( disps );
        } );

        client.subscribe( DISPOSITIVOS_INFOS_TOPIC, (message) => {
            const dispsInfos = JSON.parse( message.body );
            setDispositivosInfos( dispsInfos );     
        } );
    };

    const loadDados = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const infosResponse = await dispositivoModel.getEmpresaDispositivosInfos( empresaId );
            const dispsResponse = await dispositivoModel.listDispositivos( empresaId );
            const empResponse = await empresaModel.getEmpresa( empresaId );            

            filterDispositivos2( dispsResponse.data, '' );

            dispositivosFiltradosRef.current = dispsResponse.data;

            if ( dispsResponse.data.length == 0 )
                setInfoMessage( 'Nenhum dispositivo encontrado.' );

            setDispositivosInfos( infosResponse.data );
            setDispositivos( dispsResponse.data );
            setEmpresa( empResponse.data );

            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const filterDispositivos = async ( searchTermo : string ) => {
        await filterDispositivos2( dispositivos, searchTermo );
    };


    const filterDispositivos2 = async ( disps : DispositivoResponse[], searchTermo : string ) => {
        const lowerSearchTermo = searchTermo.toLowerCase();

        const dispsFiltrados : DispositivoResponse[] = [];
        for( let i = 0; i < disps.length; i++ ) {
            if ( disps[ i ].host.includes( searchTermo ) || 
                    disps[ i ].nome.toLowerCase().includes( lowerSearchTermo ) || 
                    disps[ i ].localizacao.toLowerCase().includes( lowerSearchTermo ) ) {
                dispsFiltrados.push( disps[ i ] );
            }
        }        
        dispositivosFiltradosRef.current = dispsFiltrados;

        setDispositivosFiltrados( dispsFiltrados );
    };

    const startEmpresaMonitoramentos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await dispositivoMonitorModel.startEmpresaMonitoramentos( empresaId );

            const response = await dispositivoModel.listDispositivos( empresaId );
            filterDispositivos2( response.data, '' );

            setDispositivos( response.data );

            setInfoMessage( 'Todos os dispositivos estão sendo monitorados.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const stopEmpresaMonitoramentos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await dispositivoMonitorModel.stopEmpresaMonitoramentos( empresaId );

            const response = await dispositivoModel.listDispositivos( empresaId );
            filterDispositivos2( response.data, '' );

            setDispositivos( response.data );

            setInfoMessage( 'Todos os monitoramentos foram finalizados.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { 
        webSocketsConnect,
        loadDados,        
        filterDispositivos, 
        startEmpresaMonitoramentos,
        stopEmpresaMonitoramentos,
        dispositivosInfos,
        empresa,
        dispositivosFiltrados,
        loading, 
        errorMessage,
        infoMessage
    };
}

export default useShowDispositivosViewModel;