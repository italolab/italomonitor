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

export const DEFAULT_CONFIG_OBJ : ConfigResponse = {
    id: 0,
    numPacotesPorLote: 0,
    monitoramentoDelay: 0,
    registroEventoPeriodo: 0,
    numThreadsLimite: 0,
    telegramBotToken: '',
    info: {
        totalMemory: 0,
        freeMemory: 0,
        maxMemory: 0,
        usoCpu: 0,
        availableProcessors: 0
    },
    monitorServers: []
};