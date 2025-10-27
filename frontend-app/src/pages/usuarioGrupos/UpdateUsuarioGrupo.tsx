import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveUsuarioGrupoViewModel from "../../viewModel/usuarioGrupo/useSaveUsuarioGrupoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveUsuarioGrupoRequest } from "../../model/dto/request/SaveUsuarioGrupoRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../util/useEffectOnce";

function UpdateUsuarioGrupo() {

    const [nome, setNome] = useState<string>( '' );

    const {
        updateUsuarioGrupo,
        getUsuarioGrupo,
        loading,
        errorMessage,
        infoMessage
    } = useSaveUsuarioGrupoViewModel();

    const { usuarioGrupoId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadUsuarioGrupo();
    } );

    const onLoadUsuarioGrupo = async () => {
        try {
            const gid : number = parseInt( usuarioGrupoId! );
            const usuarioGrupo = await getUsuarioGrupo( gid );
            setNome( usuarioGrupo.nome );

        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const usuarioGrupo : SaveUsuarioGrupoRequest = {
                nome : nome
            };
           
            const gid : number = parseInt( usuarioGrupoId! );
            await updateUsuarioGrupo( gid, usuarioGrupo );            
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
                        <h3 className="text-center m-0">Alteração de grupo</h3>
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

export default UpdateUsuarioGrupo;