
export const BASE_WS_URL = 
    ( import.meta.env.VITE_REACT_APP_BASE_WS_URL ?
        import.meta.env.VITE_REACT_APP_BASE_WS_URL :
        "ws://localhost:8080/ws/websocket" );

export const DISPOSITIVOS_TOPIC = "/user/topic/dispositivos";
export const DISPOSITIVOS_INFOS_TOPIC = "/user/topic/dispositivos-infos";