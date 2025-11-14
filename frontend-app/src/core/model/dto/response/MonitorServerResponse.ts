import type { MonitorServerInfoResponse } from "./MonitorServerInfoResponse";

export interface MonitorServerResponse {
    id : number;
    host : string;
    info : MonitorServerInfoResponse;
    ativo: boolean;
}

export const DEFAULT_MONITOR_SERVER_OBJ : MonitorServerResponse = {
    id: 0,
    host: '',
    info: {
        totalMemory: 0,
        freeMemory: 0,
        maxMemory: 0,
        availableProcessors: 0,
        usoCpu: 0,
        numThreadsAtivas: 0
    },
    ativo: false
};