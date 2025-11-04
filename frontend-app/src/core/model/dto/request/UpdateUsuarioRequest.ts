import type { UsuarioPerfil } from "../types";

export interface UpdateUsuarioRequest {
    nome : string;
    email : string;
    username : string;
    perfil: UsuarioPerfil;
    empresaId : number;
}