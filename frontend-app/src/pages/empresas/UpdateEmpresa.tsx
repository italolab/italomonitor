import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveEmpresaViewModel from "../../core/viewModel/empresa/useSaveEmpresaViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveEmpresaRequest } from "../../core/model/dto/request/SaveEmpresaRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";

function UpdateEmpresa() {

    const [nome, setNome] = useState<string>( '' );
    const [emailNotif, setEmailNotif] = useState<string>( '' );
    const [telegramChatId, setTelegramChatId] = useState<string>( '' );
    const [porcentagemMaxFalhasPorLote, setPorcentagemMaxFalhasPorLote] = useState<string>( '33.3333' );
    const [maxDispositivosQuant, setMaxDispositivosQuant] = useState<string>( '' );   
    const [minTempoParaProximoEvento, setMinTempoParaProximoEvento] = useState<string>( '' );  
    const [diaPagto, setDiaPagto] = useState<string>( '' );
    const [temporario, setTemporario] = useState<boolean>( false );
    const [usoTemporarioPor, setUsoTemporarioPor] = useState<string>( '' );

    const {
        updateEmpresa,
        getEmpresa,
        loading,
        errorMessage,
        infoMessage,
        setErrorMessage
    } = useSaveEmpresaViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadEmpresa();
    } );

    const onLoadEmpresa = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            const empresa = await getEmpresa( eid );
            setNome( empresa.nome );
            setEmailNotif( empresa.emailNotif );
            setTelegramChatId( empresa.telegramChatId )
            setPorcentagemMaxFalhasPorLote( ''+(empresa.porcentagemMaxFalhasPorLote * 100) );
            setMaxDispositivosQuant( ''+empresa.maxDispositivosQuant );
            setMinTempoParaProximoEvento( ''+empresa.minTempoParaProximoEvento );
            setDiaPagto( ''+empresa.diaPagto );
            setTemporario( empresa.temporario );
            setUsoTemporarioPor( ''+empresa.usoTemporarioPor );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        const valid = await validateForm();
        if ( valid === false )
            return;

        try {
            const empresa : SaveEmpresaRequest = {                
                nome : nome,
                emailNotif : emailNotif,
                telegramChatId: telegramChatId,
                porcentagemMaxFalhasPorLote: ( parseFloat( porcentagemMaxFalhasPorLote ) / 100.0 ),
                maxDispositivosQuant: parseInt( maxDispositivosQuant ),
                minTempoParaProximoEvento: parseInt( minTempoParaProximoEvento ),
                diaPagto: parseInt( diaPagto ),
                temporario: temporario,
                usoTemporarioPor: ( Number.isNaN( usoTemporarioPor ) === true ? 0 : parseInt( usoTemporarioPor ) )
            };
           
            const eid : number = parseInt( empresaId! );
            await updateEmpresa( eid, empresa );                        
        } catch ( error ) {
            console.error( error );
        }
    };

    const validateForm = async () => {
        if ( Number.isNaN( porcentagemMaxFalhasPorLote ) === true ) {
            setErrorMessage( 'Porcentagem máxima de falhas por lote está em formato não numérico.' );
            return false;
        }

        if ( Number.isNaN( maxDispositivosQuant ) === true ) {
            setErrorMessage( 'Quantidade máxima de dispositivos está em formato não numérico.' );
            return false;
        }

        if ( Number.isNaN( minTempoParaProximoEvento ) === true ) {
            setErrorMessage( 'O tempo mínimo para próximo evento está em formato não numérico.' );
            return false;
        }

        if ( Number.isNaN( diaPagto ) === true ) {
            setErrorMessage( 'O dia de pagamento está em formato não numérico.' );
            return false;
        }

        if ( temporario === true && Number.isNaN( usoTemporarioPor ) === true ) {
            setErrorMessage( 'Uso temporário em dias está em formato não numérico.' );
            return false;
        }

        return true;
    }

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">
                    <Card.Header>
                        <h3 className="text-center m-0">Alteração de empresa</h3>
                    </Card.Header>
                    <Card.Body>
                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                                                
                        <Form>
                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o nome"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="emailNotif">
                                <Form.Label>E-Mail de notificação</Form.Label>
                                <Form.Control type="text"
                                    value={emailNotif}
                                    onChange={ ( e ) => setEmailNotif( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="telegramChatId">
                                <Form.Label>ID de chat do telegram</Form.Label>
                                <Form.Control type="text"
                                    value={telegramChatId}
                                    onChange={ ( e ) => setTelegramChatId( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="porcentagemMaxFalhasPorLote">
                                <Form.Label>Max falhas por lote (%)</Form.Label>
                                <Form.Range min={1} max={100} step={1}
                                    value={porcentagemMaxFalhasPorLote}
                                    onChange={ ( e ) => setPorcentagemMaxFalhasPorLote( e.target.value ) } />
                                <Form.Text>
                                    Valor atual: {porcentagemMaxFalhasPorLote}%
                                </Form.Text>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="maxDispositivosQuant">
                                <Form.Label>Quant. máxima de dispositivos</Form.Label>
                                <Form.Control type="number"
                                        value={maxDispositivosQuant}
                                        onChange={( e ) => setMaxDispositivosQuant( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="minTempoParaProximoEvento">
                                <Form.Label>Tempo min. para próximo evento</Form.Label>
                                <Form.Control type="number"
                                        value={minTempoParaProximoEvento}
                                        onChange={( e ) => setMinTempoParaProximoEvento( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="diaPagto">
                                <Form.Label>Dia de pagamento</Form.Label>
                                <Form.Control type="number"
                                        value={diaPagto}
                                        onChange={( e ) => setDiaPagto( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="temporario">
                                <Form.Check type="checkbox" 
                                        label="Temporário"
                                        checked={temporario}
                                        onChange={ () => setTemporario( !temporario )}
                                        inline />
                            </Form.Group>

                            { temporario === true && 
                                <Form.Group className="mb-3" controlId="diaPagto">
                                    <Form.Label>Uso temporario em dias</Form.Label>
                                    <Form.Control type="number"
                                            value={usoTemporarioPor}
                                            onChange={( e ) => setUsoTemporarioPor( e.target.value ) } />
                                </Form.Group>
                            }

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <div className="d-flex">
                                <AppSpinner className="mx-auto" visible={loading} />
                            </div>
                        
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

export default UpdateEmpresa;