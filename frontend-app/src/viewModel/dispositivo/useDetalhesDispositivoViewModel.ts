import { useContext, useState } from "react";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { DispositivoModel } from "../../model/DispositivoModel";
import { AuthContext } from "../../context/AuthProvider";

function useDetalhesDispositivoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivo, setDispositivo] = useState<DispositivoResponse>( {
        id: 0,
        host: '',
        nome: '',
        descricao: '',
        localizacao: '',
        empresa: {
            id: 0,
            nome: '',
            emailNotif: ''
        }
    } );

    const {token} = useContext( AuthContext );

    const dispositivoModel = new DispositivoModel();

    const loadDispositivo = async ( dispositivoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

        try {
            const response = await dispositivoModel.getDispositivo( dispositivoId, token );

            setDispositivo( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadDispositivo, dispositivo, loading, errorMessage, infoMessage };
}

export default useDetalhesDispositivoViewModel;