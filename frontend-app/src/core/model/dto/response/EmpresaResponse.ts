
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
    criadoEm : Date;
    usoRegularIniciadoEm : Date;
    pagoAte : Date
}

export const DEFAULT_EMPRESA_OBJ : EmpresaResponse = {
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
    criadoEm: new Date( 0 ),
    usoRegularIniciadoEm: new Date( 0 ),
    pagoAte: new Date( 0 )
};