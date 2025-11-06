import { useContext, useRef, useState } from "react";
import { DispositivoModel } from "../../model/DispositivoModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { AuthContext } from "../../../context/AuthProvider";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { EmpresaModel } from "../../model/EmpresaModel";
import useWSDispositivoInfoRefresh from "./useWSDispositivoInfoRefresh";

function useShowDispositivosViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivos, setDispositivos] = useState<DispositivoResponse[]>( [] );
    const [dispositivosFiltrados, setDispositivosFiltrados] = useState<DispositivoResponse[]>( [] );

    const [empresa, setEmpresa] = useState<EmpresaResponse>( {
        id: 0,
        nome: '',
        emailNotif: '',
        porcentagemMaxFalhasPorLote: 0,
        maxDispositivosQuant: 0
    } );

    const dispositivosRef = useRef<DispositivoResponse[]>( [] );

    const {setAccessToken} = useContext(AuthContext);
    
    const dispositivoModel = new DispositivoModel( setAccessToken );
    const dispositivoMonitorModel = new DispositivoMonitorModel( setAccessToken );
    const empresaModel = new EmpresaModel( setAccessToken );

    const wsRefresh = useWSDispositivoInfoRefresh();

    const websocketConnect = async () => {
        return wsRefresh.connect( setDispositivoSeIDCorreto );
    };

    const setDispositivoSeIDCorreto = ( disp : DispositivoResponse ) => {
        const dispsRef : DispositivoResponse[] = dispositivosRef.current;

        alert( 'XXX' );

        const disps = [];
        for( let i = 0; i < dispsRef.length; i++ ) {                        
            disps[ i ] = dispsRef[ i ];    
            if ( disps[ i ].id === disp.id ) {
                disps[ i ] = disp;            
                alert( JSON.stringify( disp ) );
            }        
        }

        dispositivosRef.current = disps;
        setDispositivos( disps );
    };

    const loadInfos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await empresaModel.getEmpresa( empresaId );

            setEmpresa( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const loadDispositivos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await dispositivoModel.listDispositivos( empresaId );
            filterDispositivos2( response.data, '' );

            if ( response.data.length == 0 )
                setInfoMessage( 'Nenhum dispositivo encontrado.' );

            dispositivosRef.current = response.data;

            setDispositivos( response.data );
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
        const dispsFiltrados : DispositivoResponse[] = [];
        for( let i = 0; i < disps.length; i++ ) {
            if ( disps[ i ].host.includes( searchTermo ) || 
                    disps[ i ].nome.includes( searchTermo ) || 
                    disps[ i ].localizacao.includes( searchTermo ) ) {
                dispsFiltrados.push( disps[ i ] );
            }
        }
        setDispositivosFiltrados( dispsFiltrados );
    };

    const getDispositivoById = ( dispositivoId : number ) : DispositivoResponse | null => {
        for( let i = 0; i < dispositivos.length; i++ )
            if ( dispositivos[ i ].id === dispositivoId )
                return dispositivos[ i ];
        return null;
    };

    const startAllMonitoramentos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await dispositivoMonitorModel.startAllMonitoramentos( empresaId );
            setInfoMessage( 'Todos os dispositivos estão sendo monitorados.' );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const stopAllMonitoramentos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await dispositivoMonitorModel.stopAllMonitoramentos( empresaId );
            setInfoMessage( 'Todos os monitoramentos foram finalizados.' );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { 
        websocketConnect,
        loadInfos,
        loadDispositivos,
        filterDispositivos, 
        getDispositivoById,
        startAllMonitoramentos,
        stopAllMonitoramentos,
        empresa,
        dispositivosFiltrados,
        loading, 
        errorMessage,
        infoMessage
    };
}

export default useShowDispositivosViewModel;