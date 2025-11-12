import { Client, type IMessage } from "@stomp/stompjs";
import { AuthModel } from "../model/AuthModel";
import type { AxiosError } from "axios";
import { extractErrorMessage } from "../util/sistema-util";

type ReceivesMessageFunc = ( message : IMessage ) => void;
type SetErrorMessageFunc = ( message : string ) => void;

function useWebsocket() {
    
    const authModel = new AuthModel();    

    let deactivateFlag = false;
    let websocketErrorFlag = false;
    let interval = null;

    const connect = async ( 
            brokerURL : string, 
            topic : string,
            receivesMessageFunc : ReceivesMessageFunc, 
            setErrorMessageFunc : SetErrorMessageFunc ) : Promise<() => void> => {
        const response = await authModel.refreshAccessToken();

        const accessToken = response.data.accessToken;
        
        const client = new Client( {
            brokerURL: brokerURL,
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

            client.subscribe( topic, (message) => {
                receivesMessageFunc( message );   
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
                            } catch ( error2 ) {
                                const err = error2 as AxiosError;
                                if ( err.status === 401 || err.status === 403 ) {
                                    setErrorMessageFunc( extractErrorMessage( error2 ) );  

                                    clearInterval( interval! );
                                    interval = null;
                                }
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
                ( async () => await client.deactivate() )();

                if ( interval! !== null ) {
                    clearInterval( interval! );
                    interval = null;
                }
            }
        };
    };

    return { connect };

}

export default useWebsocket;