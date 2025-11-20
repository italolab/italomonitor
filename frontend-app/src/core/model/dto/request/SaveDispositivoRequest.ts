
export interface SaveDispositivoRequest {
    host : string;
    nome : string;
    descricao : string;
    localizacao : string;
    monitoradoPorAgente : boolean;
    empresaId : number;
    agenteId : number;
}