import { useState } from "react";
import { Button, Card, Form, Modal, Table } from "react-bootstrap";
import useManterAgenteViewModel from "../../core/viewModel/agente/useManterAgenteViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate, useParams } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { AgenteResponse } from "../../core/model/dto/response/AgenteResponse";
import AppPagination from "../../components/AppPagination";

function ManterAgentes() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveAgente, setToRemoveAgente] = useState<AgenteResponse|null>( null );

    const [paginationAgentes, setPaginationAgentes] = useState<AgenteResponse[]>([]);

    const { 
        filterAgentes, 
        removeAgente,
        getAgenteById,
        agentes, 
        nomePart,
        loading, 
        errorMessage, 
        infoMessage,
        setNomePart
    } = useManterAgenteViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await filterAgentes( eid );
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( agenteId : number ) => {
        setToRemoveAgente( getAgenteById( agenteId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            const eid : number = parseInt( empresaId! );
            await removeAgente( toRemoveAgente!.id, eid );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de agente</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover o agente: <br />
                    {toRemoveAgente?.nome} ?
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
                <Button type="button" onClick={() => navigate( `/create-agente/${empresaId}`)} className="func">
                    <MdAdd size={25}/> Novo agente
                </Button>
            </div>
  
            <h3 className="title">Funções de agentes</h3>

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        Campos do filtro
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
                                <th>Operações</th>
                            </tr>
                        </thead>
                        <tbody>
                            { paginationAgentes.map( (agente, index) => 
                                <tr key={index}>
                                    <td>{agente.id}</td>
                                    <td>{agente.nome}</td>
                                    <td>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-agente/${agente.id}/${empresaId}`}
                                            toEdit={`/update-agente/${agente.id}/${empresaId}`} 
                                            onRemover={() => onConfirmRemover( agente.id )} />
                                    </td>
                                </tr> 
                            )}
                        </tbody>
                    </Table>

                    <div className="d-flex justify-content-center">
                        <AppPagination 
                             dataList={agentes}
                             numberOfItemsByPage={10}
                             numberOfPagesByGroup={3}
                             onChangePageDataList={ (pageDataList : AgenteResponse[]) => setPaginationAgentes( pageDataList )}
                        />
                    </div>
                </div>                   
            </div>
        </AppLayout>
    );
}

export default ManterAgentes;