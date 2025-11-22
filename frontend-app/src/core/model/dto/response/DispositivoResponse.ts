import type { DispositivoStatus } from "../types";
import { DEFAULT_AGENTE_OBJ, type AgenteResponse } from "./AgenteResponse";
import { DEFAULT_EMPRESA_OBJ, type EmpresaResponse } from "./EmpresaResponse";

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
    monitoradoPorAgente : boolean;
    empresa : EmpresaResponse;
    agente : AgenteResponse;
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
    stateAtualizadoEm: new Date( 0 ),
    monitoradoPorAgente: false,
    empresa: DEFAULT_EMPRESA_OBJ,
    agente: DEFAULT_AGENTE_OBJ
};