import type { DispositivoStatus } from "../types";
import type { EmpresaResponse } from "./EmpresaResponse";

export interface DispositivoResponse {
    id : number;
    host : string;
    nome : string;
    descricao : string;
    localizacao : string;
    sendoMonitorado : boolean;
    status : DispositivoStatus;
    latenciaMedia : number;
    stateAtualizadoEm : Date;
    empresa : EmpresaResponse;
}

export const DEFAULT_DISPOSITIVO_OBJ : DispositivoResponse = {
    id: 0,
    host: '',
    nome: '',
    descricao: '',
    localizacao: '',
    sendoMonitorado: false,
    status : 'INATIVO',
    latenciaMedia: 0,
    stateAtualizadoEm: new Date(),
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
    }
};