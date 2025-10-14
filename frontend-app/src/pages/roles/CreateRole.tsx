import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveRoleViewModel from "../../viewModel/role/useSaveRoleViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveRoleRequest } from "../../model/dto/request/SaveRoleRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";

function CreateRole() {

    const [nome, setNome] = useState<string>( '' );

    const {
        createRole,
        loading,
        errorMessage,
        infoMessage
    } = useSaveRoleViewModel();

    const navigate = useNavigate();

    const onSave = async () => {
        try {
            const usuario : SaveRoleRequest = {
                nome : nome
            }

            await createRole( usuario );
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
                <Card className="mx-auto" style={{width: '30em'}}>
                    <Card.Header>
                        <h3 className="text-center m-0">Registro de roles</h3>
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

export default CreateRole;