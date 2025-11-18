import { useContext, useState } from "react";
import { AuthContext } from "../../../context/AuthProvider";
import { EmpresaModel } from "../../model/EmpresaModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { ConfigModel } from "../../model/ConfigModel";
import { PagamentoModel } from "../../model/PagamentoModel";

function useShowPagamentosViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const {setAccessToken} = useContext( AuthContext );

    const pagamentoModel = new PagamentoModel( setAccessToken );
    const empresaModel = new EmpresaModel( setAccessToken );
    const configModel = new ConfigModel( setAccessToken );

    const getEmpresa = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await empresaModel.getEmpresa( empresaId );

            setLoading( false );

            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getNoAdminConfig = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await configModel.getNoAdminConfig();

            setLoading( false );

            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const regularizaDivida = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await pagamentoModel.regularizaDivida( empresaId );

            setInfoMessage( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return {
        getEmpresa,
        getNoAdminConfig,
        regularizaDivida,
        errorMessage,
        infoMessage,
        loading
    };
}

export default useShowPagamentosViewModel;