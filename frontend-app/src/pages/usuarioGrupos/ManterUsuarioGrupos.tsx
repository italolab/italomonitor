import { useState } from "react";
import { Button, Card, Form, Modal, Table } from "react-bootstrap";
import useManterUsuarioGrupoViewModel from "../../viewModel/usuarioGrupo/useManterUsuarioGrupoViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";

function ManterUsuarioGrupos() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveUsuarioGrupo, setToRemoveUsuarioGrupo] = useState<UsuarioGrupoResponse|null>( null );

    const { 
        filterUsuarioGrupos, 
        removeUsuarioGrupo,
        getUsuarioGrupoById,
        usuarioGrupos, 
        nomePart,
        loading, 
        errorMessage, 
        infoMessage,
        setNomePart
    } = useManterUsuarioGrupoViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterUsuarioGrupos();
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( usuarioGrupoId : number ) => {
        setToRemoveUsuarioGrupo( getUsuarioGrupoById( usuarioGrupoId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            await removeUsuarioGrupo( toRemoveUsuarioGrupo!.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de grupos de usuarioGrupo</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover o grupo de usuarioGrupo: <br />
                    {toRemoveUsuarioGrupo?.nome} ?
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

            <h1 className="title">Funções de grupo de usuário</h1>

            <div className="d-flex justify-content-end">
                <Button type="button" onClick={() => navigate( '/create-usuario-grupo')} className="d-flex align-items-center ms-auto">
                    <MdAdd size={25}/> Novo grupo
                </Button>
            </div>

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h5 className="my-2">Campos do filtro</h5>
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
                   
                <div className="w-100 overflow-auto mt-3">
                    <Table striped bordered hover>
                        <thead>
                            <tr className="blue">
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Operações</th>
                            </tr>
                        </thead>
                        <tbody>
                            { usuarioGrupos.map( (usuarioGrupo, index) => 
                                <tr key={index}>
                                    <td>{usuarioGrupo.id}</td>
                                    <td>{usuarioGrupo.nome}</td>
                                    <td>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-usuario-grupo/${usuarioGrupo.id}`}
                                            toEdit={`/update-usuario-grupo/${usuarioGrupo.id}`} 
                                            onRemover={() => onConfirmRemover( usuarioGrupo.id)} />
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

export default ManterUsuarioGrupos;