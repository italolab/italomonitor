import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveAgenteViewModel from "../../core/viewModel/agente/useSaveAgenteViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveAgenteRequest } from "../../core/model/dto/request/SaveAgenteRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate, useParams } from "react-router-dom";
import type { AgenteResponse } from "../../core/model/dto/response/AgenteResponse";

function CreateAgente() {

    const [nome, setNome] = useState<string>( '' );

    const [chave, setChave] = useState<string|null>( null );

    const {
        createAgente,
        copyChaveToClipboard,
        loading,
        errorMessage,
        infoMessage
    } = useSaveAgenteViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    const onSave = async () => {
        try {
            const agente : SaveAgenteRequest = {
                nome : nome
            }

            const eid : number = parseInt( empresaId! );
            const resp : AgenteResponse = await createAgente( agente, eid );

            setChave( resp.chave );
            setNome( '' );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onCopyChaveToClipboard = async () => {
        try {
            await copyChaveToClipboard( chave! );
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
            
            <h3 className="title">Registro de agentes</h3>
           
            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">                    
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
                            </Form.Group>

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />                           

                            <Button type="button" onClick={onSave}>
                                Salvar 
                                <AppSpinner visible={loading} />
                            </Button>

                            { chave !== null && 
                                <>
                                    <br />
                                    <br />
                                    <h5>
                                        Chave do agente: <span className="text-primary">{chave}</span>
                                        &nbsp;&nbsp;
                                        <Button type="button" onClick={onCopyChaveToClipboard}>
                                            <span>Copiar</span>
                                        </Button>
                                    </h5>
                                </>
                            }
                        </Form>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default CreateAgente;