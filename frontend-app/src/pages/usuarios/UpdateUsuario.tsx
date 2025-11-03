import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveUsuarioViewModel from "../../core/viewModel/usuario/useSaveUsuarioViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { UpdateUsuarioRequest } from "../../core/model/dto/request/UpdateUsuarioRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import type { EmpresaResponse } from "../../core/model/dto/response/EmpresaResponse";
import useEffectOnce from "../../core/util/useEffectOnce";

function UpdateUsuario() {

    const [nome, setNome] = useState<string>( '' );
    const [email, setEmail] = useState<string>( '' );
    const [username, setUsername] = useState<string>( '' );
    const [empresaId, setEmpresaId] = useState<number>( -1 );
    const [empresas, setEmpresas] = useState<EmpresaResponse[]>( [] );

    const {
        updateUsuario,
        getUsuario,
        getEmpresas,
        loading,
        errorMessage,
        infoMessage
    } = useSaveUsuarioViewModel();

    const { usuarioId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadUsuario();
    } );

    const onLoadUsuario = async () => {
        try {
            const uid : number = parseInt( usuarioId! );
            const usuario = await getUsuario( uid );
            setNome( usuario.nome );
            setEmail( usuario.email );
            setUsername( usuario.username );

            const empresasList = await getEmpresas();
            setEmpresas( empresasList );
            setEmpresaId( usuario.empresa.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const usuario : UpdateUsuarioRequest = {
                nome : nome,
                email : email,
                username : username,
                empresaId : empresaId
            };
           
            const uid : number = parseInt( usuarioId! );
            await updateUsuario( uid, usuario );            
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
                        <h3 className="text-center m-0">Alteração de usuários</h3>
                    </Card.Header>
                    <Card.Body>
                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>

                        <Form>
                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o nome"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="email">
                                <Form.Label>E-Mail</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o email"
                                    value={email}
                                    onChange={ ( e ) => setEmail( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="username">
                                <Form.Label>Nome de usuário</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o nome de usuário"
                                    value={username}
                                    onChange={ ( e ) => setUsername( e.target.value ) } />
                            </Form.Group>

                            <Form.Group controlId="empresa">
                                <Form.Label>Empresa</Form.Label>
                                <Form.Select className="mb-3"
                                        value={empresaId} 
                                        onChange={(e) => setEmpresaId( parseInt( e.target.value ) )}>
                                    <option value={-1}>Nenhuma empresa</option>
                                    {empresas.map( (emp, index) => 
                                        <option key={index} value={emp.id}>
                                            {emp.nome}
                                        </option>
                                    )}
                                </Form.Select>
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

export default UpdateUsuario;