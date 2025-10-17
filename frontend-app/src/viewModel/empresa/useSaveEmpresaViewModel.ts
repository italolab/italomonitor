import { useContext, useState } from "react";
import { EmpresaModel } from "../../model/EmpresaModel";
import { AuthContext } from "../../context/AuthProvider";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import type { SaveEmpresaRequest } from "../../model/dto/request/SaveEmpresaRequest";


function useSaveEmpresaViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const empresaModel = new EmpresaModel();

    const {token} = useContext(AuthContext);

    const createEmpresa = async ( empresa : SaveEmpresaRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await empresaModel.createEmpresa( empresa, token );

            setInfoMessage( 'Empresa registrado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateEmpresa = async ( empresaId : number, empresa : SaveEmpresaRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await empresaModel.updateEmpresa( empresaId, empresa, token );

            setInfoMessage( 'Empresa alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getEmpresa = async ( empresaId : number ) : Promise<EmpresaResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await empresaModel.getEmpresa( empresaId, token );
            
            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        createEmpresa, 
        updateEmpresa, 
        getEmpresa, 
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveEmpresaViewModel;