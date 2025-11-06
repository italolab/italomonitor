import { useContext, useRef, useState } from "react";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { DispositivoModel } from "../../model/DispositivoModel";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";
import { AuthContext } from "../../../context/AuthProvider";
import useWSDispositivoInfoRefresh from "./useWSDispositivoInfoRefresh";
import { MENSAGEM_DELAY } from "../../constants/constants";

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
            porcentagemMaxFalhasPorLote: 0,
            maxDispositivosQuant: 0
        }
    } );

    const dispositivoIDRef = useRef( 0 );

    const {setAccessToken} = useContext(AuthContext);

    const dispositivoModel = new DispositivoModel( setAccessToken );
    const dispositivoMonitorModel = new DispositivoMonitorModel( setAccessToken );

    const wsRefresh = useWSDispositivoInfoRefresh();

    const websocketConnect = async () => {
        return wsRefresh.connect( setDispositivoSeIDCorreto );
    };

    const setDispositivoSeIDCorreto = ( disp : DispositivoResponse ) => {
        if ( dispositivoIDRef.current == disp.id )
            setDispositivo( disp );
    };

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