import { useContext, useState } from "react";
import { DispositivoModel } from "../../model/DispositivoModel";
import { AuthContext } from "../../context/AuthProvider";
import type { SaveDispositivoRequest } from "../../model/dto/request/SaveDispositivoRequest";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { EmpresaModel } from "../../model/EmpresaModel";


function useSaveDispositivoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const dispositivoModel = new DispositivoModel();
    const empresaModel = new EmpresaModel();

    const {token} = useContext(AuthContext);

    const createDispositivo = async ( dispositivo : SaveDispositivoRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await dispositivoModel.createDispositivo( dispositivo, token );

            setInfoMessage( 'Dispositivo registrado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateDispositivo = async ( dispositivoId : number, dispositivo : SaveDispositivoRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await dispositivoModel.updateDispositivo( dispositivoId, dispositivo, token );

            setInfoMessage( 'Dispositivo alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getDispositivo = async ( dispositivoId : number ) : Promise<DispositivoResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await dispositivoModel.getDispositivo( dispositivoId, token );

            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const getEmpresas = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await empresaModel.filterEmpresas( "", token );

            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { 
        createDispositivo, 
        updateDispositivo, 
        getDispositivo, 
        getEmpresas,
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveDispositivoViewModel;