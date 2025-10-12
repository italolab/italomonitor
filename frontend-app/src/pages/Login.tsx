import { Button, Card, Form } from "react-bootstrap";
import { useLoginViewModel } from "../viewModel/useLoginViewModel";
import AppMessage from "../components/AppMessage";
import AppSpinner from "../components/AppSpinner";
import { useState, type KeyboardEvent } from "react";

import { useNavigate } from 'react-router-dom';

function Login() {

    const [username, setUsername] = useState<string>( '' );
    const [senha, setSenha] = useState<string>( '' );

    const navigate = useNavigate();

    const {
        logon,
        loading,
        errorMessage,
        infoMessage
    } = useLoginViewModel();

    const login = async () => {
        try {
            await logon( {
                username: username,
                senha: senha
            } );

            navigate( '/home' );
        } catch ( error ) {
            console.error( error );
        }
    };

    const handleOnKeyDown = async ( e : KeyboardEvent<HTMLInputElement>) => {
        if ( e.key === 'Enter' )
            await login();
    };

    return (
        <div className="d-flex justify-content-center mt-5 vw-100">
            <Card style={{width: '25em'}}>
                <Card.Body>
                    <Card.Title>Login</Card.Title>
                    <Form>
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label>Username</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Informe seu username" 
                                value={username}
                                onChange={ ( e ) => setUsername( e.target.value ) }
                                onKeyDown={handleOnKeyDown} />                        
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="senha">
                            <Form.Label>Senha</Form.Label>
                            <Form.Control 
                                type="password" 
                                placeholder="Informe a senha" 
                                value={senha}
                                onChange={ ( e ) => setSenha( e.target.value ) } 
                                onKeyDown={handleOnKeyDown}/>
                        </Form.Group>

                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />
                        <Button type="button" variant="primary" onClick={login}>
                            Entrar
                            <AppSpinner visible={loading} />
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    )
}

export default Login;