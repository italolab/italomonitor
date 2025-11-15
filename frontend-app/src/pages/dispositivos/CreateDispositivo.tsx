import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveDispositivoViewModel from "../../core/viewModel/dispositivo/useSaveDispositivoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveDispositivoRequest } from "../../core/model/dto/request/SaveDispositivoRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate, useParams } from "react-router-dom";

function CreateDispositivo() {

    const [host, setHost] = useState<string>( '' );
    const [nome, setNome] = useState<string>( '' );
    const [descricao, setDescricao] = useState<string>( '' );
    const [localizacao, setLocalizacao] = useState<string>( '' );    

    const {
        createDispositivo,
        loading,
        errorMessage,
        infoMessage
    } = useSaveDispositivoViewModel();

    const { empresaId } = useParams(); 

    const navigate = useNavigate();

    const onSave = async () => {
        try {
            const dispositivo : SaveDispositivoRequest = {
                host : host,
                nome : nome,
                descricao : descricao,
                localizacao : localizacao,
                empresaId : parseInt( empresaId! )
            }

            await createDispositivo( dispositivo );

            setHost( '' );
            setNome( '' );
            setDescricao( '' );
            setLocalizacao( '' );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <h3 className="title">Registro de dispositivos</h3>

            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">                    
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="host">
                                <Form.Label>Host</Form.Label>
                                <Form.Control type="text"
                                    value={host}
                                    onChange={ ( e ) => setHost( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="descricao">
                                <Form.Label>Descrição</Form.Label>
                                <Form.Control as="textarea" rows={4}
                                    value={descricao}
                                    onChange={ ( e ) => setDescricao( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="localizacao">
                                <Form.Label>Localização</Form.Label>
                                <Form.Control as="textarea" rows={4}
                                    value={localizacao}
                                    onChange={ ( e ) => setLocalizacao( e.target.value ) } />
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

export default CreateDispositivo;