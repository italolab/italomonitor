
export interface AgenteResponse {
    id : number;
    chave : string;
    nome : string;
    dispositivosQuant : number;
}

export const DEFAULT_AGENTE_OBJ : AgenteResponse = {
    id : 0,
    chave : '',
    nome : '',
    dispositivosQuant: 0
};