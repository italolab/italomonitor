import { useContext } from "react";
import { AuthContext } from "../../../context/AuthProvider";
import { Client } from "@stomp/stompjs";
import type { DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { AuthModel } from "../../model/AuthModel";
import { BASE_WS_URL, DISPOSITIVOS_TOPIC } from "../../constants/websocket-constants";

function useWSDispositivoInfoRefresh() {

    type SetDispositivoFunc = ( d : DispositivoResponse ) => void;

    const {accessToken, setAccessToken} = useContext(AuthContext);

    const authModel = new AuthModel();    

    const websocketConfig = {
        brokerURL: BASE_WS_URL,
        connectHeaders: {
            Authorization: `Bearer ${accessToken}`
        },
        heartbeatOutgoing: 10000,
        heartbeatIncoming: 10000,            
    };

    let deactivateFlag = false;
    let websocketErrorFlag = false;
    let interval = null;

    const connect = ( setDispositivo : SetDispositivoFunc ) : () => void => {
        const client = new Client( websocketConfig );
        client.onConnect = () => {       
            websocketErrorFlag = false;
            if ( interval! !== null ) {
                clearInterval( interval! );
                interval = null;
            }

            client.subscribe( DISPOSITIVOS_TOPIC, (message) => {
                const data = JSON.parse( message.body );
                setDispositivo( data );                       
            } );
        } 
        client.onWebSocketError = () => {
            ( async () => {
                if ( websocketErrorFlag === false ) {                    
                    try {
                        const response = await authModel.refreshAccessToken();
                        client.connectHeaders.Authorization = `Bearer ${ response.data.accessToken }`;                    

                        setAccessToken( response.data.accessToken );

                    // eslint-disable-next-line @typescript-eslint/no-unused-vars
                    } catch ( error ) {              
                        if ( interval! !== null )
                            clearInterval( interval! );                        
                                
                        interval = setInterval( async () => {
                            try {
                                const response2 = await authModel.refreshAccessToken();
                                client.connectHeaders.Authorization = `Bearer ${ response2.data.accessToken }`;                    

                                setAccessToken( response2.data.accessToken );
                            
                                clearInterval( interval! );
                                interval = null;

                            // eslint-disable-next-line @typescript-eslint/no-unused-vars
                            } catch ( error2 ) {
                                console.log( "tentando atualizar o token.")
                            }
                        }, 10000 );
                    }

                    websocketErrorFlag = true;
                }
            } )();
        };

        client.activate();
        
        return () => {
            if ( deactivateFlag === false ) {
                deactivateFlag = true;
            } else {
                client.deactivate();
                
                if ( interval! !== null ) {
                    clearInterval( interval! );
                    interval = null;
                }
            }
        };
    };

    return { connect };

}

export default useWSDispositivoInfoRefresh;