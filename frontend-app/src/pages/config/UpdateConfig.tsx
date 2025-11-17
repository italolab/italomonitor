import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveConfigViewModel from "../../core/viewModel/config/useSaveConfigViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate } from "react-router-dom";
import type { SaveConfigRequest } from "../../core/model/dto/request/SaveConfigRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";
import type { ConfigResponse } from "../../core/model/dto/response/ConfigResponse";

function UpdateConfig() {

    const [numPacotesPorLote, setNumPacotesPorLote] = useState<string>( '' );
    const [monitoramentoDelay, setMonitoramentoDelay] = useState<string>( '' );
    const [registroEventoPeriodo, setRegistroEventoPeriodo] = useState<string>( '' );
    const [numThreadsLimite, setNumThreadsLimite] = useState<string>( '' );
    const [telegramBotToken, setTelegramBotToken] = useState<string>( '' );
    const [valorPagto, setValorPagto] = useState<string>( '' );

    const {
        updateConfig,
        getConfig,
        loading,
        errorMessage,
        infoMessage,
        setErrorMessage
    } = useSaveConfigViewModel();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadConfig();
    } );

    const onLoadConfig = async () => {
        try {
            const config : ConfigResponse = await getConfig();
            setNumPacotesPorLote( ""+config.numPacotesPorLote );
            setMonitoramentoDelay( ""+config.monitoramentoDelay );
            setRegistroEventoPeriodo( ""+config.registroEventoPeriodo );                      
            setNumThreadsLimite( ""+config.numThreadsLimite );
            setTelegramBotToken( config.telegramBotToken );
            setValorPagto( ''+config.valorPagto );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        const valid = await validaSave();
        if ( valid === false )
            return;

        try {
            const config : SaveConfigRequest = {
                numPacotesPorLote: parseInt( numPacotesPorLote ),
                monitoramentoDelay: parseInt( monitoramentoDelay ),
                registroEventoPeriodo: parseInt( registroEventoPeriodo ),
                numThreadsLimite: parseInt( numThreadsLimite ),
                telegramBotToken: telegramBotToken,
                valorPagto: parseFloat( valorPagto )
            };
           
            await updateConfig( config );            
        } catch ( error ) {
            console.error( error );
        }
    };

    const validaSave = async () => {
        if ( Number.isNaN( numPacotesPorLote ) === true ) {
            setErrorMessage( 'O número de pacotes por lote está em formato não numérico.' );
            return false;
        }
        if ( Number.isNaN( monitoramentoDelay ) === true ) {
            setErrorMessage( 'O delay de monitoramento está em formato não numérico.' );
            return false;
        }
        if ( Number.isNaN( registroEventoPeriodo ) === true ) {
            setErrorMessage( 'O período de registro de evento está em formato não numérico.' );
            return false;
        }
        if ( Number.isNaN( numThreadsLimite ) === true ) {
            setErrorMessage( 'O limite de threads está em formato não numérico.' );
            return false;
        }

        if ( Number.isNaN( valorPagto ) === true ) {
            setErrorMessage( 'O valor de pagamento está em formato não numérico.' );
            return false;
        }

        return true;
    };


    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>
            
            <h3 className="title">Alteração de configurações</h3>
       
            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="numPacotesPorLote">
                                <Form.Label>Número de pacotes por lote</Form.Label>
                                <Form.Control type="number"
                                    placeholder="Informe o número de pacotes"
                                    value={numPacotesPorLote}
                                    onChange={ ( e ) => setNumPacotesPorLote( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="monitoramentoDelay">
                                <Form.Label>Delay de monitoramento (ms)</Form.Label>
                                <Form.Control type="number"
                                    placeholder="Informe o delay"
                                    value={monitoramentoDelay}
                                    onChange={ ( e ) => setMonitoramentoDelay( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="registroEventoPeriodo">
                                <Form.Label>Período de registro de eventos (segundos)</Form.Label>
                                <Form.Control type="number"
                                    placeholder="Informe o período"
                                    value={registroEventoPeriodo}
                                    onChange={ ( e ) => setRegistroEventoPeriodo( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="numThreadsLimite">
                                <Form.Label>Limite de threads</Form.Label>
                                <Form.Control type="number"
                                    placeholder="Informe o limite de threads"
                                    value={numThreadsLimite}
                                    onChange={ ( e ) => setNumThreadsLimite( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="telegramBotToken">
                                <Form.Label>Token do bot do telegram</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o token"
                                    value={telegramBotToken}
                                    onChange={ ( e ) => setTelegramBotToken( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="valorPagto">
                                <Form.Label>Valor de pagamento</Form.Label>
                                <Form.Control type="number"
                                    placeholder="Informe o valor"
                                    value={valorPagto}
                                    onChange={ ( e ) => setValorPagto( e.target.value ) } />
                            </Form.Group>

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <Button type="button" onClick={onSave}>
                                Salvar 
                                <AppSpinner visible={loading} />
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default UpdateConfig;