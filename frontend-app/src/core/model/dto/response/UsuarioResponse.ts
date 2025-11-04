import type { UsuarioPerfil } from "../types";
import type { EmpresaResponse } from "./EmpresaResponse";
import type { UsuarioGrupoResponse } from "./UsuarioGrupoResponse";

export interface UsuarioResponse {
    id : number;
    nome : string;
    email : string;
    username : string;
    perfil: UsuarioPerfil;
    empresa : EmpresaResponse;
    grupos : UsuarioGrupoResponse[];
}