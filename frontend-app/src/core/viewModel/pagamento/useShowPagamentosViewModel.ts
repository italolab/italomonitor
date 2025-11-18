import { useContext, useState } from "react";
import { AuthContext } from "../../../context/AuthProvider";
import { extractErrorMessage } from "../../util/sistema-util";
import { PagamentoModel } from "../../model/PagamentoModel";
import { DEFAULT_PAGS_OBJ, type PagamentosResponse } from "../../model/dto/response/PagamentosResponse";

function useShowPagamentosViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [pagamentosDados, setPagamentosDados] = useState<PagamentosResponse>( DEFAULT_PAGS_OBJ );

    const {setAccessToken} = useContext( AuthContext );

    const pagamentoModel = new PagamentoModel( setAccessToken );

    const load = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await pagamentoModel.getPagamentos( empresaId );

            setPagamentosDados( response.data );
            setLoading( false );
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
        load,
        regularizaDivida,
        pagamentosDados,
        errorMessage,
        infoMessage,
        loading
    };
}

export default useShowPagamentosViewModel;