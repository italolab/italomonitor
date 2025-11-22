import { DEFAULT_MONITOR_SERVER_INFO_OBJ, type MonitorServerInfoResponse } from "./MonitorServerInfoResponse";

export interface MonitorServerResponse {
    id : number;
    host : string;
    info : MonitorServerInfoResponse;
    ativo: boolean;
}

export const DEFAULT_MONITOR_SERVER_OBJ : MonitorServerResponse = {
    id: 0,
    host: '',
    info: DEFAULT_MONITOR_SERVER_INFO_OBJ,
    ativo: false
};