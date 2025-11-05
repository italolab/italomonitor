import { useContext, useRef, useState } from "react";
import { DispositivoModel } from "../../model/DispositivoModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { AuthContext } from "../../../context/AuthProvider";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { EmpresaModel } from "../../model/EmpresaModel";
import useWSDispositivoInfoRefresh from "./useWSDispositivoInfoRefresh";

function useManterDispositivoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivos, setDispositivos] = useState<DispositivoResponse[]>( [] );

    const [hostPart, setHostPart] = useState<string>( '' );
    const [nomePart, setNomePart] = useState<string>( '' );
    const [localPart, setLocalPart] = useState<string>( '' );

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

    const websocketConnect = () => {
        return wsRefresh.connect( setDispositivoSeIDCorreto );
    };

    const setDispositivoSeIDCorreto = ( disp : DispositivoResponse ) => {
        const dispsRef : DispositivoResponse[] = dispositivosRef.current;

        const disps = [];
        for( let i = 0; i < dispsRef.length; i++ ) {                        
            disps[ i ] = dispsRef[ i ];    
            if ( disps[ i ].id === disp.id )
                disps[ i ] = disp;                    
        }

        dispositivosRef.current = disps;
        setDispositivos( disps );
        return;                   
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

    const filterDispositivos = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await dispositivoModel.filterDispositivos( empresaId, hostPart, nomePart, localPart );

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

    const removeDispositivo = async ( dispositivoId : number, empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await dispositivoModel.deleteDispositivo( dispositivoId );
            const response = await dispositivoModel.filterDispositivos( empresaId, hostPart, nomePart, localPart );

            setDispositivos( response.data );
            setInfoMessage( 'Dispositivo deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
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
        filterDispositivos, 
        removeDispositivo,         
        getDispositivoById,
        startAllMonitoramentos,
        stopAllMonitoramentos,
        empresa,
        dispositivos, 
        hostPart,
        nomePart,    
        localPart,    
        loading, 
        errorMessage,
        infoMessage,
        setHostPart,
        setNomePart,
        setLocalPart,
        setErrorMessage
    };
}

export default useManterDispositivoViewModel;