import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveEmpresaViewModel from "../../viewModel/empresa/useSaveEmpresaViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveEmpresaRequest } from "../../model/dto/request/SaveEmpresaRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../util/useEffectOnce";

function UpdateEmpresa() {

    const [nome, setNome] = useState<string>( '' );
    const [emailNotif, setEmailNotif] = useState<string>( '' );
    const [porcentagemMaxFalhasPorLote, setPorcentagemMaxFalhasPorLote] = useState<number>( 33.3333 );
    

    const {
        updateEmpresa,
        getEmpresa,
        loading,
        errorMessage,
        infoMessage
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
            setPorcentagemMaxFalhasPorLote( empresa.porcentagemMaxFalhasPorLote * 100 );

        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const empresa : SaveEmpresaRequest = {
                nome : nome,
                emailNotif : emailNotif,
                porcentagemMaxFalhasPorLote: ( porcentagemMaxFalhasPorLote / 100.0 )
            };
           
            const eid : number = parseInt( empresaId! );
            await updateEmpresa( eid, empresa );            
        } catch ( error ) {
            console.error( error );
        }
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
                        <h3 className="text-center m-0">Alteração de empresa</h3>
                    </Card.Header>
                    <Card.Body>
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

                            <Form.Group className="mb-3" controlId="porcentagemMaxFalhasPorLote">
                                <Form.Label>Max falhas por lote (%)</Form.Label>
                                <Form.Range min={1} max={100} step={1}
                                    value={porcentagemMaxFalhasPorLote}
                                    onChange={ ( e ) => setPorcentagemMaxFalhasPorLote( parseFloat(e.target.value ) ) } />
                                <Form.Text>
                                    Valor atual: {porcentagemMaxFalhasPorLote}%
                                </Form.Text>
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

export default UpdateEmpresa;