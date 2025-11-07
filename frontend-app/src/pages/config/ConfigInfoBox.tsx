import { Badge } from "react-bootstrap";
import type { ConfigResponse } from "../../core/model/dto/response/ConfigResponse";
import { FaServer } from "react-icons/fa6";
import AppField from "../../components/AppField";

interface ConfigInfosBoxProps {
    config: ConfigResponse;
}

function ConfigInfoBox( { config } : ConfigInfosBoxProps ) {
    return (
        <>            
            <AppField name="número de pacotes por lote">
                {config.numPacotesPorLote}
            </AppField>                  
            <AppField name="delay de monitoramento">
                {config.monitoramentoDelay}
            </AppField>      
            <AppField name="período de registro de evento">
                {config.registroEventoPeriodo}
            </AppField>
            <AppField name="limite de threads">
                {config.numThreadsLimite}
            </AppField>
            <AppField name="servidores de monitoramento">
                { config.monitorServers.map( (monitor, index) => 
                    <div key={index}>
                        <Badge bg="primary" className="my-1 d-inline-flex align-items-center">
                            <FaServer size={14}/> &nbsp; 
                            {monitor.host}
                        </Badge>
                        &nbsp;
                        -
                        &nbsp;
                        {monitor.ativo === true ? 
                            <span className="text-success fw-bold">Ativo</span>: 
                            <span className="text-danger fw-bold">Inativo</span>
                        }
                        &nbsp;
                        -
                        &nbsp;
                        {monitor.numThreadsAtivas} threads ativas
                    </div>
                )}
            </AppField> 
        </>
    )
}

export default ConfigInfoBox;