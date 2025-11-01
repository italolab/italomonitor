import type { EmpresaResponse } from "./EmpresaResponse";

export type DispositivoStatus = "ATIVO" | "INATIVO";

export interface DispositivoResponse {
    id : number;
    host : string;
    nome : string;
    descricao : string;
    localizacao : string;
    sendoMonitorado : boolean;

    status : DispositivoStatus;
    empresa : EmpresaResponse;
}