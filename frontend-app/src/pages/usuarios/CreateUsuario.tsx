import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveUsuarioViewModel from "../../viewModel/usuario/useSaveUsuarioViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { CreateUsuarioRequest } from "../../model/dto/request/CreateUsuarioRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";

function CreateUsuario() {

    const [nome, setNome] = useState<string>( '' );
    const [email, setEmail] = useState<string>( '' );
    const [username, setUsername] = useState<string>( '' );
    const [senha, setSenha] = useState<string>( '' );
    const [senha2, setSenha2] = useState<string>( '' );

    const {
        createUsuario,
        loading,
        errorMessage,
        infoMessage,
        setErrorMessage
    } = useSaveUsuarioViewModel();

    const navigate = useNavigate();

    const onSave = async () => {
        const valid : boolean = await validateForm();
        if( valid === false )
            return;

        try {
            const usuario : CreateUsuarioRequest = {
                nome : nome,
                email : email,
                username : username,
                senha : senha
            }

            await createUsuario( usuario );
        } catch ( error ) {
            console.error( error );
        }
    };

    const validateForm = async () : Promise<boolean> => {
        if ( senha !== senha2 ) {
           setErrorMessage( 'As senhas informadas não correspondem.' );
           return false;
        }
        return true;
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
                        <h3 className="text-center m-0">Registro de usuários</h3>
                    </Card.Header>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="email">
                                <Form.Label>E-Mail</Form.Label>
                                <Form.Control type="text"
                                    value={email}
                                    onChange={ ( e ) => setEmail( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="username">
                                <Form.Label>Nome de usuário</Form.Label>
                                <Form.Control type="text"
                                    value={username}
                                    onChange={ ( e ) => setUsername( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="senha">
                                <Form.Label>Senha</Form.Label>
                                <Form.Control type="password"
                                    value={senha}
                                    onChange={ ( e ) => setSenha( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="senha2">
                                <Form.Label>Confirme a senha</Form.Label>
                                <Form.Control type="password"
                                    value={senha2}
                                    onChange={ ( e ) => setSenha2( e.target.value ) } />
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

export default CreateUsuario;