import type { UsuarioPerfil } from "../types";
import { DEFAULT_EMPRESA_OBJ, type EmpresaResponse } from "./EmpresaResponse";
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

export const DEFAULT_USUARIO_OBJ : UsuarioResponse = {
    id: 0,
    nome: '',
    email: '',
    username: '',
    perfil: 'ADMIN',
    empresa: DEFAULT_EMPRESA_OBJ,
    grupos: []
};