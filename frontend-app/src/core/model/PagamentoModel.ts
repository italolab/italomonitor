import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";


export class PagamentoModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async getPagamentoPixQrCode( empresaId : number ) {
        return await api.get( '/pagamentos/empresa/'+empresaId+"/get/pix-qrcode" );
    }

}