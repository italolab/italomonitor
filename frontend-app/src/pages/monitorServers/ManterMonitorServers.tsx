import { useState } from "react";
import { Button, Modal, Table } from "react-bootstrap";
import useManterMonitorServerViewModel from "../../core/viewModel/monitorServer/useManterMonitorServerViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd, MdArrowBack } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { MonitorServerResponse } from "../../core/model/dto/response/MonitorServerResponse";
import useEffectOnce from "../../core/util/useEffectOnce";

function ManterMonitorServers() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveMonitorServer, setToRemoveMonitorServer] = useState<MonitorServerResponse|null>( null );

    const { 
        loadMonitorServers, 
        removeMonitorServer,
        getMonitorServerById,
        monitorServers, 
        loading, 
        errorMessage, 
        infoMessage,
    } = useManterMonitorServerViewModel();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        try {
            await loadMonitorServers();
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
            
            <div className="d-flex justify-content-start">
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25}/> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( '/create-monitor-server')} className="func">
                    <MdAdd size={25}/> Novo servidor
                </Button>
            </div>
  
            <h3 className="title">Lista de servidores de monitoramento</h3>     

            <AppMessage message={errorMessage} type="error" />
            <AppMessage message={infoMessage} type="info" />

            <div className="d-flex">
                <AppSpinner className="mx-auto" visible={loading} />
            </div>

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
        </AppLayout>
    );
}

export default ManterMonitorServers;