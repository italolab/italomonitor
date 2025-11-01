import { Button, Card, Col, Form, Row, Table } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useListEventosByDiaViewModel from "../../core/viewModel/evento/useListEventosByDiaViewModel";
import { useState } from "react";
import type { EventoResponse } from "../../core/model/dto/response/EventoResponse";
import AppPagination from "../../components/AppPagination";
import { dataToString, formataDataHora, formataTempo } from "../../core/util/sistema-util";
import { useParams } from "react-router-dom";

function ListEventosByIntervalo() {

    const [paginationEventos, setPaginationEventos] = useState<EventoResponse[]>( [] );
    const [dataDiaIni, setDataDiaIni] = useState<string>( dataToString( new Date() ) );
    const [dataDiaFim, setDataDiaFim] = useState<string>( dataToString( new Date() ) );

    const {
        loadEventosByIntervalo,
        eventos,
        loading,
        errorMessage,
        infoMessage
    } = useListEventosByDiaViewModel();

    const { dispositivoId } = useParams();

    const onListEventos = async () => {
        try {
            const did : number = parseInt( dispositivoId! );
            await loadEventosByIntervalo( did, dataDiaIni, dataDiaFim );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <>
            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h5 className="my-2">Campos do filtro</h5>
                    </Card.Header>
                    <Card.Body className="p-3">
                        <Form>
                            <Row>
                                <Col>
                                    <Form.Group className="mb-3" controlId="dataDiaIni">
                                        <Form.Label>Data inicial</Form.Label>
                                        <Form.Control type="date" 
                                            placeholder="Informe a data de início"
                                            value={dataDiaIni}
                                            onChange={ (e) => setDataDiaIni( e.target.value ) } />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group className="mb-3" controlId="dataDiaFim">
                                        <Form.Label>Data final</Form.Label>
                                        <Form.Control type="date" 
                                            placeholder="Informe a data de fim"
                                            value={dataDiaFim}
                                            onChange={ (e) => setDataDiaFim( e.target.value ) } />
                                    </Form.Group>
                                </Col>
                            </Row>                                                        

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <Button type="button" onClick={onListEventos}>
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
                                <th>Sucessos</th>
                                <th>Falhas</th>
                                <th>Quedas</th>
                                <th>Inatividade</th>
                                <th>Duração - hh:mm:ss</th>
                                <th>Criado em</th>
                            </tr>
                        </thead>
                        <tbody>
                            { paginationEventos.map( (evento, index) => 
                                <tr key={index}>
                                    <td>{evento.sucessosQuant}</td>
                                    <td>{evento.falhasQuant}</td>
                                    <td>{evento.quedasQuant}</td>
                                    <td>{formataTempo( evento.tempoInatividade )}</td>
                                    <td>{formataTempo( evento.duracao )}</td>
                                    <td>{formataDataHora( evento.criadoEm )}</td>                                    
                                </tr> 
                            )}
                        </tbody>
                    </Table>

                    <div className="d-flex justify-content-center">
                        <AppPagination 
                             dataList={eventos}
                             numberOfItemsByPage={10}
                             numberOfPagesByGroup={3}
                             onChangePageDataList={ (pageDataList : EventoResponse[]) => setPaginationEventos( pageDataList )}
                        />
                    </div>
                </div>                   
            </div>
        </>
    )
}

export default ListEventosByIntervalo;