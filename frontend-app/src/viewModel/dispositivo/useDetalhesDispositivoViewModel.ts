import { useState } from "react";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { DispositivoModel } from "../../model/DispositivoModel";
import { DispositivoMonitorModel } from "../../model/DispositivoMonitorModel";

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

    const dispositivoModel = new DispositivoModel();
    const dispositivoMonitorModel = new DispositivoMonitorModel();

    const loadDispositivo = async ( dispositivoId : number ) => {
        setErrorMessage( null );
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
        dispositivo, 
        loading, 
        errorMessage, 
        infoMessage, 
        setDispositivo 
    };
}

export default useDetalhesDispositivoViewModel;