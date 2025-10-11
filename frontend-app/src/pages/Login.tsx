import { Button, Card, Form } from "react-bootstrap";
import { useLoginViewModel } from "../viewModel/useLoginViewModel";
import AppMessage from "../components/AppMessage";
import AppSpinner from "../components/AppSpinner";
import { useState } from "react";

function Login() {

    const [username, setUsername] = useState<string>( '' );
    const [senha, setSenha] = useState<string>( '' );

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
            alert( errorMessage );
        } catch ( error ) {
            console.error( error );
            alert( errorMessage );
        }
    };

    return (
        <div className="d-flex justify-content-center mt-5 vw-100">
            <Card style={{width: '18em' }}>
                <Card.Body>
                    <Card.Title>Login</Card.Title>
                    <Form>
                        <Form.Group className="mb-3" controlId="username">
                            <Form.Label>Username</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Informe seu username" 
                                value={username}
                                onChange={ ( e ) => setUsername( e.target.value ) } />                        
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="senha">
                            <Form.Label>Senha</Form.Label>
                            <Form.Control 
                                type="password" 
                                placeholder="Informe a senha" 
                                value={senha}
                                onChange={ ( e ) => setSenha( e.target.value ) } />
                        </Form.Group>

                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />
                        <Button type="submit" variant="primary" onClick={login}>
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