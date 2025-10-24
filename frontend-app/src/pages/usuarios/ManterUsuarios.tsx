import { useState } from "react";
import { Button, Card, Form, Modal, Table } from "react-bootstrap";
import useManterUsuarioViewModel from "../../viewModel/usuario/useManterUsuarioViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { UsuarioResponse } from "../../model/dto/response/UsuarioResponse";

function ManterUsuarios() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveUsuario, setToRemoveUsuario] = useState<UsuarioResponse|null>( null );

    const { 
        filterUsuarios, 
        removeUsuario,
        getUsuarioById,
        usuarios, 
        nomePart,
        loading, 
        errorMessage, 
        infoMessage,
        setNomePart
    } = useManterUsuarioViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterUsuarios();
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( usuarioId : number ) => {
        setToRemoveUsuario( getUsuarioById( usuarioId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            await removeUsuario( toRemoveUsuario!.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header closeButton>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de usuários</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover o usuário: <br />
                    {toRemoveUsuario?.nome} ?
                </Modal.Body>
                <Modal.Footer className="d-flex justify-content-end">
                    <Button type="button" className="mx-2" onClick={ () => setRemoveModalVisible( false ) }>
                        Cancelar
                    </Button>
                    <Button variant="danger" type="button" onClick={onRemover}>
                        Remover
                    </Button>
                </Modal.Footer>
            </Modal>            

            <div className="d-flex justify-content-end">
                <Button type="button" onClick={() => navigate( '/create-usuario')} className="d-flex align-items-center ms-auto">
                    <MdAdd size={25}/> Novo usuário
                </Button>
            </div>

            <h3 className="title">Funções de usuário</h3>

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h5 className="m-0">Campos do filtro</h5>
                    </Card.Header>
                    <Card.Body className="p-3">
                        <Form>
                            <Form.Group className="mb-3" controlId="nomepart">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text" 
                                    placeholder="Informe parte do nome"
                                    value={nomePart}
                                    onChange={ (e) => setNomePart( e.target.value ) } />
                            </Form.Group>

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <Button type="button" onClick={onFilter}>
                                Filtrar                        
                                <AppSpinner visible={loading} />
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
                   
                <div className="w-100 overflow-auto">
                    <Table striped bordered hover className="mt-3">
                        <thead>
                            <tr className="blue">
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Empresa</th>
                                <th>Operações</th>
                            </tr>
                        </thead>
                        <tbody>
                            { usuarios.map( (usuario, index) => 
                                <tr key={index}>
                                    <td>{usuario.id}</td>
                                    <td>{usuario.nome}</td>
                                    <td>{usuario.empresa != null ? usuario.empresa.nome : 'Nenhuma empresa'}</td>
                                    <td>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-usuario/${usuario.id}`}
                                            toEdit={`/update-usuario/${usuario.id}`} 
                                            onRemover={() => onConfirmRemover( usuario.id)} />
                                    </td>
                                </tr> 
                            )}
                        </tbody>
                    </Table>
                </div>                   
            </div>
        </AppLayout>
    );
}

export default ManterUsuarios;