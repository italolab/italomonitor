import { useContext, useState } from "react";
import { EmpresaModel } from "../../model/EmpresaModel";
import { AuthContext } from "../../../context/AuthProvider";
import { extractErrorMessage } from "../../util/sistema-util";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";
import { DispositivoModel } from "../../model/DispositivoModel";
import type { DispositivosInfosResponse } from "../../model/dto/response/DispositivosInfosResponse";

function useUsuarioDashboardViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [empresa, setEmpresa] = useState<EmpresaResponse>( {
        id: 0,
        nome: '',
        emailNotif: '',
        telegramChatId: '',
        porcentagemMaxFalhasPorLote: 0,
        maxDispositivosQuant: 0,
        minTempoParaProxNotif: 0,
        diaPagto: 0,
        temporario: false,
        usoTemporarioPor: 0,
        criadoEm: new Date()
    } );

    const [dispositivosInfos, setDispositivosInfos] = useState<DispositivosInfosResponse>( {
        quantTotal: 0,
        sendoMonitoradosQuant: 0
    } );

    const {setAccessToken} = useContext(AuthContext);

    const empresaModel = new EmpresaModel( setAccessToken );
    const dispositivoModel = new DispositivoModel( setAccessToken );

    const load = async ( empresaId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const empresaResponse = await empresaModel.getEmpresa( empresaId );
            const dispsInfosResponse = await dispositivoModel.getEmpresaDispositivosInfos( empresaId );

            setEmpresa( empresaResponse.data );
            setDispositivosInfos( dispsInfosResponse.data );

            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false )
            throw error;
        }
    };

    return {    
        load,
        empresa,
        dispositivosInfos,    
        errorMessage,
        infoMessage,
        loading,
        setErrorMessage
    };
}

export default useUsuarioDashboardViewModel;