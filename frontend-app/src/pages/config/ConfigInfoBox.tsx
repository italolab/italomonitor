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
                {config.monitoramentoDelay} milisegundos
            </AppField>      
            <AppField name="período de registro de evento">
                {config.registroEventoPeriodo} segundos
            </AppField>
            <AppField name="limite de threads">
                {config.numThreadsLimite}
            </AppField>
            <AppField name="token do bot telegram">
                {config.telegramBotToken}
            </AppField>

            <small className="text-primary">Infos. de monitoramento</small>
            <br />

            <span className="text-dark fw-bold">
                {config.numDispositivosSendoMonitorados}
            </span>
            &nbsp; dispositivos sendo monitorados
            <br />
            <br />

            <small className="text-primary">Recursos do microserviço principal</small>
            <br />
            
            <span className="text-dark fw-bold">
                {Math.floor( ( config.info.totalMemory - config.info.freeMemory ) / (1024*1024) )} MB
            </span>
            &nbsp; de memória utilizada
            <br />
            <span className="text-dark fw-bold">
                {Math.floor( config.info.totalMemory / (1024*1024) )} MB
            </span>
            &nbsp; de memória alocada
            <br />
            <span className="text-dark fw-bold">
                {Math.floor( config.info.maxMemory / (1024*1024) )} MB
            </span>
            &nbsp; de memoria disponível para a JVM
            <br />
            <span className="text-dark fw-bold">
                {(config.info.usoCpu * 100).toFixed( 4 )}%
            </span>
            &nbsp; de cpu
            <br />
            <span className="text-dark fw-bold">
                {config.info.availableProcessors}
            </span>
            &nbsp; núcleos de processador 
            <br />
            
            <br />

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

                        { monitor.info && 
                            <>
                                <span>{ monitor.info.numThreadsAtivas } threads ativas</span>
                            
                                <br />
                                <div className="mx-5">
                                    <span className="text-dark fw-bold">
                                        {Math.floor( ( monitor.info.totalMemory - monitor.info.freeMemory ) / (1024*1024) )} MB
                                    </span>
                                    &nbsp; de memória utilizada
                                    <br />
                                    <span className="text-dark fw-bold">
                                        {Math.floor( monitor.info.totalMemory / (1024*1024) )} MB
                                    </span>
                                    &nbsp; de memória alocada
                                    <br />
                                    <span className="text-dark fw-bold">
                                        {Math.floor( monitor.info.maxMemory / (1024*1024) )} MB
                                    </span>
                                    &nbsp; de memoria disponível para a JVM
                                    <br />
                                    <span className="text-dark fw-bold">
                                        {(monitor.info.usoCpu * 100).toFixed( 4 )}%
                                    </span>
                                    &nbsp; de cpu
                                    <br />
                                    <span className="text-dark fw-bold">
                                        {monitor.info.availableProcessors}
                                    </span>
                                    &nbsp; núcleos de processador
                                </div>                  
                            </>
                        }
                    </div>
                )}
            </AppField> 
        </>
    )
}

export default ConfigInfoBox;