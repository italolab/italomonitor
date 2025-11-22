import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveAgenteViewModel from "../../core/viewModel/agente/useSaveAgenteViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveAgenteRequest } from "../../core/model/dto/request/SaveAgenteRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";

function UpdateAgente() {

    const [nome, setNome] = useState<string>( '' );

    const {
        updateAgente,
        getAgente,
        loading,
        errorMessage,
        infoMessage
    } = useSaveAgenteViewModel();

    const { agenteId, empresaId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadAgente();
    } );

    const onLoadAgente = async () => {
        try {
            const rid : number = parseInt( agenteId! );
            const agente = await getAgente( rid );
            setNome( agente.nome );

        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const agente : SaveAgenteRequest = {
                nome : nome
            };
           
            const rid : number = parseInt( agenteId! );
            const eid : number = parseInt( empresaId! );
            await updateAgente( rid, agente, eid );            
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
            
            <h3 className="title">Alteração de agente</h3>
           
            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">                    
                    <Card.Body>                                                                        
                        <Form>                            
                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o nome"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
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

export default UpdateAgente;