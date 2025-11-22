import { DEFAULT_MAIN_API_INFO_OBJ, type MainAPIInfoResponse } from "./MainAPIInfoResponse";
import type { MonitorServerResponse } from "./MonitorServerResponse";

export interface ConfigResponse {
    id : number;
    numPacotesPorLote : number;
    monitoramentoDelay : number;
    registroEventoPeriodo : number;
    numThreadsLimite : number;
    telegramBotToken : string;
    numDispositivosSendoMonitorados : number;
    valorPagto : number;
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
    numDispositivosSendoMonitorados: 0,
    valorPagto: 0,
    info: DEFAULT_MAIN_API_INFO_OBJ,
    monitorServers: []
};