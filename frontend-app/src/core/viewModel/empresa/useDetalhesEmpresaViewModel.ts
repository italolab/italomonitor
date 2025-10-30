import { useContext, useState } from "react";
import { type EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { EmpresaModel } from "../../model/EmpresaModel";
import { AuthContext } from "../../../context/AuthProvider";


function useDetalhesEmpresaViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [empresa, setEmpresa] = useState<EmpresaResponse>( {
        id: 0,
        nome: '',
        emailNotif: '',
        porcentagemMaxFalhasPorLote: 0
    } );
    
    const {setAccessToken} = useContext(AuthContext);

    const empresaModel = new EmpresaModel( setAccessToken );

    const loadEmpresa = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

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

    return { loadEmpresa, empresa, loading, errorMessage, infoMessage };
}

export default useDetalhesEmpresaViewModel;