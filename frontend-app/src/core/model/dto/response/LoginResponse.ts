import type { UsuarioPerfil } from "../types";

export interface LoginResponse {
    nome : string;
    username : string;
    accessToken : string; 
    usuarioId : number; 
    empresaId : number;
    perfil : UsuarioPerfil;  
}