import { useContext, useState } from "react";
import { DispositivoModel } from "../../model/DispositivoModel";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { AuthContext } from "../../context/AuthProvider";
import { type DispositivoResponse } from "../../model/dto/response/DispositivoResponse";

function useManterDispositivoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [dispositivos, setDispositivos] = useState<DispositivoResponse[]>( [] );

    const [hostPart, setHostPart] = useState<string>( '' );
    const [nomePart, setNomePart] = useState<string>( '' );
    const [localPart, setLocalPart] = useState<string>( '' );

    const {token} = useContext(AuthContext);

    const dispositivoModel = new DispositivoModel();

    const filterDispositivos = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await dispositivoModel.filterDispositivos( hostPart, nomePart, localPart, token );

            if ( response.data.length == 0 )
                setInfoMessage( 'Nenhum dispositivo encontrado.' );

            setDispositivos( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeDispositivo = async ( dispositivoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await dispositivoModel.deleteDispositivo( dispositivoId, token );
            const response = await dispositivoModel.filterDispositivos( hostPart, nomePart, localPart, token );

            setDispositivos( response.data );
            setInfoMessage( 'Dispositivo deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getDispositivoById = ( dispositivoId : number ) : DispositivoResponse | null => {
        for( let i = 0; i < dispositivos.length; i++ )
            if ( dispositivos[ i ].id === dispositivoId )
                return dispositivos[ i ];
        return null;
    };

    return { 
        filterDispositivos, 
        removeDispositivo,         
        getDispositivoById,
        dispositivos, 
        hostPart,
        nomePart,    
        localPart,    
        loading, 
        errorMessage,
        infoMessage,
        setHostPart,
        setNomePart,
        setLocalPart
    };
}

export default useManterDispositivoViewModel;