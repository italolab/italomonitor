import AppField from "../../components/AppField";
import type { EmpresaResponse } from "../../core/model/dto/response/EmpresaResponse";

interface EmpresaInfoBoxProps {
    empresa : EmpresaResponse;
}

function EmpresaInfoBox( { empresa } : EmpresaInfoBoxProps ) {
    return (
        <>
            <AppField name="ID">
                {empresa.id}
            </AppField>
            <AppField name="nome">
                {empresa.nome}
            </AppField>      
            <AppField name="e-mail de notificação">
                {empresa.emailNotif}
            </AppField>
            <AppField name="id de chat telegram">
                {empresa.telegramChatId}
            </AppField>
            <AppField name="max falhas por lote (%)">
                {empresa.porcentagemMaxFalhasPorLote * 100} {"%"}
            </AppField>
            <AppField name="quantidade máxima de dispositivos">
                {empresa.maxDispositivosQuant}
            </AppField>
            <AppField name="tempo min. para próx. notificação">
                {empresa.minTempoParaProxNotif} segundos
            </AppField>
            <AppField name="dia de pagamento">
                {empresa.diaPagto}
            </AppField>
            <AppField name="temporário">
                {empresa.temporario === true ? "Sim" : "Não"}
            </AppField>
            <AppField name="uso temporário por">
                {empresa.usoTemporarioPor} dias
            </AppField>
            <AppField name="bloqueada">
                {empresa.bloqueada === true ? "Sim" : "Não" } 
            </AppField>
        </>
    )
}

export default EmpresaInfoBox;