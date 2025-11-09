
export interface EmpresaResponse {
    id : number;
    nome : string;
    emailNotif : string;
    telegramChatId : string;
    porcentagemMaxFalhasPorLote : number;    
    maxDispositivosQuant : number;
    minTempoParaProximoEvento : number;
    diaPagto : number;
    temporario : boolean;
    usoTemporarioPor : number;
    criadoEm : Date
}