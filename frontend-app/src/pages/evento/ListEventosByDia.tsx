import { Button, Card, Form, Table } from "react-bootstrap";
import AppLayout from "../../layout/AppLayout";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useListEventosByDiaViewModel from "../../viewModel/evento/useListEventosByDiaViewModel";
import { useState } from "react";
import type { EventoResponse } from "../../model/dto/response/EventoResponse";
import AppPagination from "../../components/AppPagination";
import { dataToString, formataDataHora, formataEmMinutos } from "../../util/sistema-util";
import { useParams } from "react-router-dom";

function ListEventosByDia() {

    const [paginationEventos, setPaginationEventos] = useState<EventoResponse[]>( [] );
    const [dataDia, setDataDia] = useState<string>( dataToString( new Date() ) );

    const {
        loadEventosByDia,
        eventos,
        loading,
        errorMessage,
        infoMessage
    } = useListEventosByDiaViewModel();

    const { dispositivoId } = useParams();

    const onListEventosByDia = async () => {
        try {
            const did : number = parseInt( dispositivoId! );
            await loadEventosByDia( did, dataDia );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <h3 className="title">Funções de empresas</h3>

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h5 className="my-2">Campos do filtro</h5>
                    </Card.Header>
                    <Card.Body className="p-3">
                        <Form>
                            <Form.Group className="mb-3" controlId="nomepart">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="date" 
                                    placeholder="Informe a data"
                                    value={dataDia}
                                    onChange={ (e) => setDataDia( e.target.value ) } />
                            </Form.Group>

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <Button type="button" onClick={onListEventosByDia}>
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
                                <th>Duração</th>
                                <th>Criado em</th>
                            </tr>
                        </thead>
                        <tbody>
                            { paginationEventos.map( (evento, index) => 
                                <tr key={index}>
                                    <td>{evento.sucessosQuant}</td>
                                    <td>{evento.falhasQuant}</td>
                                    <td>{evento.quedasQuant}</td>
                                    <td>{formataEmMinutos( evento.tempoInatividade )}</td>
                                    <td>{formataEmMinutos( evento.duracao )}</td>
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
        </AppLayout>
    )
}

export default ListEventosByDia;