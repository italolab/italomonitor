import type { UsuarioPerfil } from "../types";

export interface LoginResponse {
    nome : string;
    username : string;
    accessToken : string;  
    empresaId : number;
    perfil : UsuarioPerfil;  
}