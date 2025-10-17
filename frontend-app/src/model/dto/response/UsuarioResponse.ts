import type { EmpresaResponse } from "./EmpresaResponse";

export interface UsuarioResponse {
    id : number;
    nome : string;
    email : string;
    username : string;
    empresa : EmpresaResponse;
}