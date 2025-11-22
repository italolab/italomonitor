
export interface AgenteResponse {
    id : number;
    chave : string;
    nome : string;
    ultimoEnvioDeEstadoEm : Date;
    dispositivosQuant : number;
}

export const DEFAULT_AGENTE_OBJ : AgenteResponse = {
    id : 0,
    chave : '',
    nome : '',
    ultimoEnvioDeEstadoEm: new Date( 0 ),
    dispositivosQuant: 0
};