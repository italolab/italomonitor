import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveRoleViewModel from "../../viewModel/role/useSaveRoleViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveRoleRequest } from "../../model/dto/request/SaveRoleRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../util/useEffectOnce";

function UpdateRole() {

    const [nome, setNome] = useState<string>( '' );

    const {
        updateRole,
        getRole,
        loading,
        errorMessage,
        infoMessage
    } = useSaveRoleViewModel();

    const { roleId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadRole();
    } );

    const onLoadRole = async () => {
        try {
            const rid : number = parseInt( roleId! );
            const role = await getRole( rid );
            setNome( role.nome );

        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const role : SaveRoleRequest = {
                nome : nome
            };
           
            const rid : number = parseInt( roleId! );
            await updateRole( rid, role );            
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
                        <h3 className="text-center m-0">Alteração de role</h3>
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

export default UpdateRole;