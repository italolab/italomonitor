import { useState } from "react";
import { Button, Card, Form, Modal, Table } from "react-bootstrap";
import useManterRoleViewModel from "../../core/viewModel/role/useManterRoleViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { RoleResponse } from "../../core/model/dto/response/RoleResponse";
import AppPagination from "../../components/AppPagination";

function ManterRoles() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveRole, setToRemoveRole] = useState<RoleResponse|null>( null );

    const [paginationRoles, setPaginationRoles] = useState<RoleResponse[]>([]);

    const { 
        filterRoles, 
        removeRole,
        getRoleById,
        roles, 
        nomePart,
        loading, 
        errorMessage, 
        infoMessage,
        setNomePart
    } = useManterRoleViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterRoles();
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( roleId : number ) => {
        setToRemoveRole( getRoleById( roleId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            await removeRole( toRemoveRole!.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de role</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover o role: <br />
                    {toRemoveRole?.nome} ?
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
                <Button type="button" onClick={() => navigate( '/create-role')} className="d-flex align-items-center ms-auto">
                    <MdAdd size={25}/> Novo role
                </Button>
            </div>
  
            <h3 className="title">Funções de roles</h3>

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
                            { paginationRoles.map( (role, index) => 
                                <tr key={index}>
                                    <td>{role.id}</td>
                                    <td>{role.nome}</td>
                                    <td>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-role/${role.id}`}
                                            toEdit={`/update-role/${role.id}`} 
                                            onRemover={() => onConfirmRemover( role.id )} />
                                    </td>
                                </tr> 
                            )}
                        </tbody>
                    </Table>

                    <div className="d-flex justify-content-center">
                        <AppPagination 
                             dataList={roles}
                             numberOfItemsByPage={3}
                             numberOfPagesByGroup={2}
                             onChangePageDataList={ (pageDataList : RoleResponse[]) => setPaginationRoles( pageDataList )}
                        />
                    </div>
                </div>                   
            </div>
        </AppLayout>
    );
}

export default ManterRoles;