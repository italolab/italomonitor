import { useContext, useState } from "react";
import { AuthContext } from "../../../context/AuthProvider";
import { EmpresaModel } from "../../model/EmpresaModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { ConfigModel } from "../../model/ConfigModel";

function useShowPagamentosViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const {setAccessToken} = useContext( AuthContext );

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

    return {
        getEmpresa,
        getNoAdminConfig,
        errorMessage,
        infoMessage,
        loading
    };
}

export default useShowPagamentosViewModel;