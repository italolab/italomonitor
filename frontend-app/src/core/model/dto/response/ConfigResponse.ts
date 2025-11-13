import type { MainAPIInfoResponse } from "./MainApiInfoResponse";
import type { MonitorServerResponse } from "./MonitorServerResponse";

export interface ConfigResponse {
    id : number;
    numPacotesPorLote : number;
    monitoramentoDelay : number;
    registroEventoPeriodo : number;
    numThreadsLimite : number;
    telegramBotToken : string;
    info : MainAPIInfoResponse;
    monitorServers : MonitorServerResponse[];
}