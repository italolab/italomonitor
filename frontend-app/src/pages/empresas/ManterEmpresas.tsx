import { useState } from "react";
import { Button, Card, Form, Modal, Table } from "react-bootstrap";
import useManterEmpresaViewModel from "../../core/viewModel/empresa/useManterEmpresaViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { EmpresaResponse } from "../../core/model/dto/response/EmpresaResponse";
import AppPagination from "../../components/AppPagination";

function ManterEmpresas() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveEmpresa, setToRemoveEmpresa] = useState<EmpresaResponse|null>( null );

    const [paginationEmpresas, setPaginationEmpresas] = useState<EmpresaResponse[]>([]);

    const { 
        filterEmpresas, 
        removeEmpresa,
        getEmpresaById,
        empresas, 
        nomePart,
        loading, 
        errorMessage, 
        infoMessage,
        setNomePart
    } = useManterEmpresaViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterEmpresas();
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( empresaId : number ) => {
        setToRemoveEmpresa( getEmpresaById( empresaId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            await removeEmpresa( toRemoveEmpresa!.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de empresa</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover a empresa: <br />
                    {toRemoveEmpresa?.nome} ?
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

            <div>
                <Button type="button" onClick={() => navigate( '/create-empresa')} className="func">
                    <MdAdd size={25}/> Nova empresa
                </Button>
            </div>

            <h3 className="title">Funções de empresas</h3>

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h5 className="m-0">CAMPOS DE FILTRO</h5>
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
                    <Table striped hover>
                        <thead>
                            <tr className="blue">
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Notificar</th>
                                <th>Operações</th>
                            </tr>
                        </thead>
                        <tbody>
                            { paginationEmpresas.map( (empresa, index) => 
                                <tr key={index}>
                                    <td>{empresa.id}</td>
                                    <td>{empresa.nome}</td>
                                    <td>{empresa.emailNotif}</td>
                                    <td>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-empresa/${empresa.id}`}
                                            toEdit={`/update-empresa/${empresa.id}`} 
                                            onRemover={() => onConfirmRemover( empresa.id )} />
                                    </td>
                                </tr> 
                            )}
                        </tbody>
                    </Table>

                    <div className="d-flex justify-content-center">
                        <AppPagination 
                             dataList={empresas}
                             numberOfItemsByPage={10}
                             numberOfPagesByGroup={3}
                             onChangePageDataList={ (pageDataList : EmpresaResponse[]) => setPaginationEmpresas( pageDataList )}
                        />
                    </div>
                </div>                   
            </div>
        </AppLayout>
    );
}

export default ManterEmpresas;