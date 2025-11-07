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
            <AppField name="max falhas por lote (%)">
                {empresa.porcentagemMaxFalhasPorLote * 100} {"%"}
            </AppField>
            <AppField name="quantidade máxima de dispositivos">
                {empresa.maxDispositivosQuant}
            </AppField>
        </>
    )
}

export default EmpresaInfoBox;