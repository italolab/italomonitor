import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";


export class PagamentoModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async getPagamentoPixQrCode( empresaId : number ) {
        return await api.get( '/pagamentos/empresa/'+empresaId+"/get/pix-qrcode" );
    }

    async regularizaDivida( empresaId : number ) {
        return await api.post( '/pagamentos/empresa/'+empresaId+'/regulariza-divida' );
    }

    async getPagamentos( empresaId : number ) {
        return await api.get( '/pagamentos/empresa/'+empresaId+'/list' );
    }

}