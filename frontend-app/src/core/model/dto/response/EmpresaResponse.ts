
export interface EmpresaResponse {
    id : number;
    nome : string;
    emailNotif : string;
    telegramChatId : string;
    porcentagemMaxFalhasPorLote : number;    
    maxDispositivosQuant : number;
    minTempoParaProxNotif : number;
    diaPagto : number;
    temporario : boolean;
    usoTemporarioPor : number;
    bloqueada : boolean;
    criadoEm : Date
}