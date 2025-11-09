
export interface NoAdminSaveEmpresaRequest {
    nome : string;
    emailNotif : string;
    telegramChatId : string;
    porcentagemMaxFalhasPorLote : number;
    minTempoParaProxNotif : number;
}