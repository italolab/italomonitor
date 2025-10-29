import { useContext, useState } from "react";
import { AuthContext } from "../../context/AuthProvider";
import { ConfigModel } from "../../model/ConfigModel";
import type { SaveConfigRequest } from "../../model/dto/request/SaveConfigRequest";
import { extractErrorMessage } from "../../util/sistema-util";

function useSaveConfigViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const { setAccessToken } = useContext(AuthContext);

    const configModel = new ConfigModel( setAccessToken );

    const updateConfig = async ( config : SaveConfigRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try{
            await configModel.updateConfig( config );

            setInfoMessage( 'Configurações salvas com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const getConfig = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try{
            const response = await configModel.getConfig();
            
            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { updateConfig, getConfig, errorMessage, infoMessage, loading, setErrorMessage };
}

export default useSaveConfigViewModel;