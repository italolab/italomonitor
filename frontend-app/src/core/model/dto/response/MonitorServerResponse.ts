import type { MonitorServerInfoResponse } from "./MonitorServerInfoResponse";

export interface MonitorServerResponse {
    id : number;
    host : string;
    info : MonitorServerInfoResponse;
    ativo: boolean;
}