import { useState } from "react";
import { Button, Card, Col, Form, Modal, Row } from "react-bootstrap";
import useManterDispositivoViewModel from "../../viewModel/dispositivo/useManterDispositivoViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";
import type { DispositivoResponse } from "../../model/dto/response/DispositivoResponse";

import './style/ManterDispositivos.css'

function ManterDispositivos() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );
    const [toRemoveDispositivo, setToRemoveDispositivo] = useState<DispositivoResponse|null>( null );

    const { 
        filterDispositivos, 
        removeDispositivo,
        getDispositivoById,
        dispositivos, 
        hostPart,
        nomePart,
        localPart,
        loading, 
        errorMessage, 
        infoMessage,
        setHostPart,
        setNomePart,
        setLocalPart
    } = useManterDispositivoViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterDispositivos();
        } catch ( error ) {
            console.log( error );
        }
    };

    const onConfirmRemover = async ( dispositivoId : number ) => {
        setToRemoveDispositivo( getDispositivoById( dispositivoId ) );
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            await removeDispositivo( toRemoveDispositivo!.id );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>    
            <Modal show={removeModalVisible} onHide={() => setRemoveModalVisible( false ) }>
                <Modal.Header closeButton>
                    <Modal.Title>
                        <h3 className="m-0">Remoção de dispositivos</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tem certeza que deseja remover o dispositivo: <br />
                    {toRemoveDispositivo?.nome} ?
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

            <h3 className="title">Funções de dispositivo</h3>

            <div className="d-flex justify-content-end">
                <Button type="button" onClick={() => navigate( '/create-dispositivo')} className="d-flex align-items-center ms-auto">
                    <MdAdd size={25}/> Novo dispositivo
                </Button>
            </div>

            <div className="d-block w-100 mt-3 d-flex justify-content-center">
                <Card>
                    <Card.Header>
                        <h5 className="m-0">Campos do filtro</h5>
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

                            <Row>
                                <Col>
                                    <Form.Group className="mb-3" controlId="nomepart">
                                        <Form.Label>Nome</Form.Label>
                                        <Form.Control as="textarea" rows={2}
                                            placeholder="Informe parte do nome"
                                            value={nomePart}
                                            onChange={ (e) => setNomePart( e.target.value ) } />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group className="mb-3" controlId="localpart">
                                        <Form.Label>Localização</Form.Label>
                                        <Form.Control as="textarea" rows={2}
                                            placeholder="Informe parte da localização"
                                            value={localPart}
                                            onChange={ (e) => setLocalPart( e.target.value ) } />
                                    </Form.Group>
                                </Col>
                            </Row>                            

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <Button type="button" onClick={onFilter}>
                                Filtrar                        
                                <AppSpinner visible={loading} />
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </div>
                                
            <div className="d-flex justify-content-center mt-3">
                <Row className="disp-row w-100">
                    { dispositivos.map( (dispositivo, index) =>                 
                        <Col key={index} sm={4} className="disp-col">
                            <div className="disp-card">
                                <div className="d-flex align-items-center justify-content-between">
                                    <span className="text-dark">
                                        {dispositivo.id}
                                    </span>
                                    <span>
                                        <AppOperations 
                                            toDetalhes={`/detalhes-dispositivo/${dispositivo.id}`}
                                            toEdit={`/update-dispositivo/${dispositivo.id}`} 
                                            onRemover={() => onConfirmRemover( dispositivo.id)} />
                                    </span>
                                </div>
                                <div className="mb-3">{dispositivo.nome}</div>
                                <div>{dispositivo.localizacao}</div>                            
                            </div>
                        </Col> 
                    )}
                </Row>
            </div>                   
        </AppLayout>
    );
}

export default ManterDispositivos;