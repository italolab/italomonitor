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
                registroEventoPeriodo: parseInt( registroEventoPeriodo )
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

        return true;
    };


    return (
        <AppLayout>
            <div className="d-flex justify-content-start">
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">
                    <Card.Header>
                        <h3 className="text-center m-0">Alteração de configurações</h3>
                    </Card.Header>
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

                            <Form.Group className="mb-3" controlId="numPacotesPorLote">
                                <Form.Label>Número de pacotes por lote</Form.Label>
                                <Form.Control type="number"
                                    placeholder="Informe o número de pacotes"
                                    value={numPacotesPorLote}
                                    onChange={ ( e ) => setNumPacotesPorLote( e.target.value ) } />
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