import { useContext, useState } from "react";
import { extractErrorMessage } from "../../util/sistema-util";
import { PagamentoModel } from "../../model/PagamentoModel";
import { AuthContext } from "../../../context/AuthProvider";
import { DEFAULT_PAG_PIX_QRCODE_OBJ, type PagamentoPixQrCodeResponse } from "../../model/dto/response/PagamentoPixQrCodeResponse";

function useEfetuarPagamentoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [pixQrCode, setPixQrCode] = useState<PagamentoPixQrCodeResponse>( DEFAULT_PAG_PIX_QRCODE_OBJ );

    const { setAccessToken } = useContext(AuthContext);

    const pagamentoModel = new PagamentoModel( setAccessToken );

    const loadPixQrCode = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            const response = await pagamentoModel.getPagamentoPixQrCode( empresaId );

            console.log( response.data.text );

            setPixQrCode( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return {
        loadPixQrCode,
        pixQrCode,
        errorMessage,
        infoMessage,
        loading
    };
}

export default useEfetuarPagamentoViewModel;