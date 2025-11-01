import { useState } from "react";
import { Button, Card, Form, Modal, Table } from "react-bootstrap";
import useManterMonitorServerViewModel from "../../core/viewModel/monitorServer/useManterMonitorServerViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { MonitorServerResponse } from "../../core/model/dto/response/MonitorServerResponse";

function ManterMonitorServers() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveMonitorServer, setToRemoveMonitorServer] = useState<MonitorServerResponse|null>( null );

    const { 
        filterMonitorServers, 
        removeMonitorServer,
        getMonitorServerById,
        monitorServers, 
        hostPart,
        loading, 
        errorMessage, 
        infoMessage,
        setHostPart
    } = useManterMonitorServerViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterMonitorServers();
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( monitorServerId : number ) => {
        setToRemoveMonitorServer( getMonitorServerById( monitorServerId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            await removeMonitorServer( toRemoveMonitorServer!.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de monitorServer</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover o servidor de monitoramento: <br />
                    {toRemoveMonitorServer?.host} ?
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
                <Button type="button" onClick={() => navigate( '/create-monitor-server')} className="d-flex align-items-center ms-auto">
                    <MdAdd size={25}/> Novo servidor
                </Button>
            </div>
  
            <h3 className="title">Funções de servidor de monitoramento</h3>

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h5 className="my-2">Campos do filtro</h5>
                    </Card.Header>
                    <Card.Body className="p-3">
                        <Form>
                            <Form.Group className="mb-3" controlId="hostpart">
                                <Form.Label>Host</Form.Label>
                                <Form.Control type="text" 
                                    placeholder="Informe parte do host"
                                    value={hostPart}
                                    onChange={ (e) => setHostPart( e.target.value ) } />
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
                                <th>Host</th>
                                <th>Operações</th>
                            </tr>
                        </thead>
                        <tbody>
                            { monitorServers.map( (monitorServer, index) => 
                                <tr key={index}>
                                    <td>{monitorServer.id}</td>
                                    <td>{monitorServer.host}</td>
                                    <td>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-monitor-server/${monitorServer.id}`}
                                            toEdit={`/update-monitor-server/${monitorServer.id}`} 
                                            onRemover={() => onConfirmRemover( monitorServer.id )} />
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

export default ManterMonitorServers;