import { useContext, useState } from "react";
import { AuthContext } from "../../context/AuthProvider";
import { EventoModel } from "../../model/EventoModel";
import type { EventoResponse } from "../../model/dto/response/EventoResponse";
import { extractErrorMessage } from "../../util/sistema-util";

function useListEventosByDiaViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [eventos, setEventos] = useState<EventoResponse[]>( [] );

    const {setAccessToken} = useContext(AuthContext);

    const eventoModel = new EventoModel( setAccessToken );

    const loadEventosByDia = async ( dispositivoId : number, dataDia : string ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await eventoModel.listByDia( dispositivoId, dataDia );

            setEventos( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { loadEventosByDia, eventos, errorMessage, infoMessage, loading }

}

export default useListEventosByDiaViewModel;