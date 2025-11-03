import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveUsuarioGrupoViewModel from "../../core/viewModel/usuarioGrupo/useSaveUsuarioGrupoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveUsuarioGrupoRequest } from "../../core/model/dto/request/SaveUsuarioGrupoRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";

function CreateUsuarioGrupo() {

    const [nome, setNome] = useState<string>( '' );

    const {
        createUsuarioGrupo,
        loading,
        errorMessage,
        infoMessage
    } = useSaveUsuarioGrupoViewModel();

    const navigate = useNavigate();

    const onSave = async () => {
        try {
            const usuario : SaveUsuarioGrupoRequest = {
                nome : nome
            }

            await createUsuarioGrupo( usuario );

            setNome( '' );
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

            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">
                    <Card.Header>
                        <h3 className="text-center m-0">Registro de grupos</h3>
                    </Card.Header>
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

export default CreateUsuarioGrupo;