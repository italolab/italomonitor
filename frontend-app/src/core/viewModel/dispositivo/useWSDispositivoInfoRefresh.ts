import { Client } from "@stomp/stompjs";
import type { DispositivoResponse } from "../../model/dto/response/DispositivoResponse";
import { AuthModel } from "../../model/AuthModel";
import { BASE_WS_URL, DISPOSITIVOS_TOPIC } from "../../constants/websocket-constants";

type SetDispositivoSeIDCorretoFunc = ( d : DispositivoResponse ) => void;

function useWSDispositivoInfoRefresh() {
    const authModel = new AuthModel();    

    let deactivateFlag = false;
    let websocketErrorFlag = false;
    let interval = null;

    const connect = async ( setDispositivoSeIDCorreto : SetDispositivoSeIDCorretoFunc ) : Promise<() => void> => {
        const response = await authModel.refreshAccessToken();

        const accessToken = response.data.accessToken;

        const client = new Client( {
            brokerURL: BASE_WS_URL,
            connectHeaders: {
                Authorization: `Bearer ${accessToken}`
            },
            heartbeatOutgoing: 10000,
            heartbeatIncoming: 10000,
        } );
        client.onConnect = () => {      
            websocketErrorFlag = false;
            if ( interval! !== null ) {
                clearInterval( interval! );
                interval = null;
            }

            client.subscribe( DISPOSITIVOS_TOPIC, (message) => {
                const data = JSON.parse( message.body );
                setDispositivoSeIDCorreto( data );   
            } );
        } 
        client.onWebSocketError = () => {
            ( async () => {
                if ( websocketErrorFlag === false ) {                    
                    try {
                        const response = await authModel.refreshAccessToken();
                        client.connectHeaders.Authorization = `Bearer ${ response.data.accessToken }`;      

                    // eslint-disable-next-line @typescript-eslint/no-unused-vars
                    } catch ( error ) {              
                        if ( interval! !== null )
                            clearInterval( interval! );                        
                                
                        interval = setInterval( async () => {
                            try {
                                const response2 = await authModel.refreshAccessToken();
                                client.connectHeaders.Authorization = `Bearer ${ response2.data.accessToken }`;                    
                            
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