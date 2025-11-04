import type { UsuarioPerfil } from "../types";

export interface CreateUsuarioRequest {
    nome : string;
    email : string;
    username : string;
    senha : string;
    perfil : UsuarioPerfil;
    empresaId : number;
}