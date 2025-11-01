import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveEmpresaViewModel from "../../core/viewModel/empresa/useSaveEmpresaViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveEmpresaRequest } from "../../core/model/dto/request/SaveEmpresaRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";

function CreateEmpresa() {

    const [nome, setNome] = useState<string>( '' );
    const [emailNotif, setEmailNotif] = useState<string>( '' );
    const [porcentagemMaxFalhasPorLote, setPorcentagemMaxFalhasPorLote] = useState<number>( 33.3333 );

    const {
        createEmpresa,
        loading,
        errorMessage,
        infoMessage
    } = useSaveEmpresaViewModel();

    const navigate = useNavigate();

    const onSave = async () => {
        try {
            const empresa : SaveEmpresaRequest = {
                nome : nome,
                emailNotif : emailNotif,
                porcentagemMaxFalhasPorLote: ( porcentagemMaxFalhasPorLote / 100.0 )
            }

            await createEmpresa( empresa );
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
                        <h3 className="text-center m-0">Registro de empresas</h3>
                    </Card.Header>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
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

export default CreateEmpresa;