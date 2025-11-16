import { useContext, useState } from "react";
import { DEFAULT_EMPRESA_OBJ, type EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { AuthContext } from "../../../context/AuthProvider";
import { EmpresaModel } from "../../model/EmpresaModel";
import { extractErrorMessage } from "../../util/sistema-util";

function useShowPagamentosViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [empresa, setEmpresa] = useState<EmpresaResponse>( DEFAULT_EMPRESA_OBJ );

    const {setAccessToken} = useContext( AuthContext );

    const empresaModel = new EmpresaModel( setAccessToken );

    const load = async ( empresaId : number ) => {
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

    return {
        load,
        empresa,
        errorMessage,
        infoMessage,
        loading
    };
}

export default useShowPagamentosViewModel;