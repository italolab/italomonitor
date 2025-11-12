import { Button, Card, Form } from "react-bootstrap";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useAlterSenhaViewModel from "../../core/viewModel/usuario/useAlterSenhaViewModel";
import { useNavigate, useParams } from "react-router-dom";
import useEffectOnce from "../../core/util/useEffectOnce";
import { useState } from "react";
import type { AlterSenhaRequest } from "../../core/model/dto/request/AlterSenhaRequest";


function AlterSenha() {

    const [ novaSenha, setNovaSenha ] = useState<string>( '' );
    const [ novaSenha2, setNovaSenha2 ] = useState<string>( '' );

    const {
        load,
        alterSenha,
        usuario,
        errorMessage,
        infoMessage,
        loading,
        setErrorMessage
    } = useAlterSenhaViewModel();

    const { usuarioId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        try {
            const uid : number = parseInt( usuarioId! );
            await load( uid );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onAlterSenha = async () => {
        const valid = validateForm();
        if ( valid === false )
            return;

        try {
            const alterSenhaSave : AlterSenhaRequest = {
                novaSenha: novaSenha
            };  

            const uid : number = parseInt( usuarioId! );
            await alterSenha( uid, alterSenhaSave );
        } catch ( error ) {
            console.error( error );
        }
    };

    const validateForm = () => {
        if ( novaSenha != novaSenha2 ) {
            setErrorMessage( 'A nova senha e a confirmação dela não correspondem.' );
            return false;
        }

        return true;
    }

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25} /> Voltar
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-2">
                <Card>
                    <Card.Header>
                        <h3 className="m-0 text-center">Alteração de senha</h3>
                    </Card.Header>
                    <Card.Body>                        
                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
                        <h6>Usuário: <span className="text-primary">{usuario.nome}</span></h6>

                        <Form.Group className="mb-3" controlId="novaSenha">
                            <Form.Label>Senha</Form.Label>
                            <Form.Control type="password"
                                value={novaSenha}
                                onChange={ ( e ) => setNovaSenha( e.target.value ) } />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="novaSenha2">
                            <Form.Label>Confirme a senha</Form.Label>
                            <Form.Control type="password"
                                value={novaSenha2}
                                onChange={ ( e ) => setNovaSenha2( e.target.value ) } />
                        </Form.Group>

                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <Button type="button" onClick={onAlterSenha}>
                            Alterar senha 
                            <AppSpinner visible={loading} />
                        </Button>                        
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default AlterSenha;