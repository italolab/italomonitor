import type { DispositivoStatus } from "../types";
import type { EmpresaResponse } from "./EmpresaResponse";

export interface DispositivoResponse {
    id : number;
    host : string;
    nome : string;
    descricao : string;
    localizacao : string;
    sendoMonitorado : boolean;
    status : DispositivoStatus;
    latenciaMedia : number;
    stateAtualizadoEm : Date;
    empresa : EmpresaResponse;
}