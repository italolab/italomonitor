import { useContext, useState } from "react";
import { EmpresaModel } from "../../model/EmpresaModel";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { AuthContext } from "../../context/AuthProvider";

function useManterEmpresaViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [empresas, setEmpresas] = useState<EmpresaResponse[]>( [] );

    const [nomePart, setNomePart] = useState<string>( '' );
    
    const {setAccessToken} = useContext(AuthContext);

    const empresaModel = new EmpresaModel( setAccessToken );

    const filterEmpresas = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await empresaModel.filterEmpresas( nomePart );

            setEmpresas( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeEmpresa = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await empresaModel.deleteEmpresa( empresaId );
            const response = await empresaModel.filterEmpresas( nomePart );

            setEmpresas( response.data );
            setInfoMessage( 'Empresa deletada com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getEmpresaById = ( empresaId : number ) : EmpresaResponse | null => {
        for( let i = 0; i < empresas.length; i++ )
            if ( empresas[ i ].id === empresaId )
                return empresas[ i ];
        return null;
    };

    return { 
        filterEmpresas, 
        removeEmpresa,         
        getEmpresaById,
        empresas, 
        nomePart,        
        loading, 
        errorMessage,
        infoMessage,
        setNomePart
    };
}

export default useManterEmpresaViewModel;