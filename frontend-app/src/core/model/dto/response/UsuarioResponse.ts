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

export const DEFAULT_USUARIO_OBJ : UsuarioResponse = {
    id: 0,
    nome: '',
    email: '',
    username: '',
    perfil: 'ADMIN',
    empresa: {
        id: 0,
        nome: '',
        emailNotif: '',
        telegramChatId: '',
        porcentagemMaxFalhasPorLote: 0,
        maxDispositivosQuant: 0,
        minTempoParaProxNotif: 0,
        diaPagto: 0,
        temporario: false,
        usoTemporarioPor: 0,
        bloqueada: false,
        criadoEm: new Date()
    },
    grupos: []
};