import type { EmpresaResponse } from "./EmpresaResponse";

export interface DispositivoResponse {
    id : number;
    host : string;
    nome : string;
    descricao : string;
    localizacao : string;
    empresa : EmpresaResponse;
}