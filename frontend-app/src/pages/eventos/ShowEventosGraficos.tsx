import { Button, Card, Col, Form, Nav, Row } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useListEventosByDiaViewModel from "../../core/viewModel/evento/useListEventosByDiaViewModel";
import { useState } from "react";
import { useParams } from "react-router-dom";
import { dataToString, formataDataHoraSemSegundos } from "../../core/util/sistema-util";
import { CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis } from "recharts";

import './style/ShowEventosGraficos.css'
import type { EventoResponse } from "../../core/model/dto/response/EventoResponse";

function ShowEventosGraficos() {

    const DADOS_QUANT = 8;

    const PACOTES_GRAFICO = 'pacotes-grafico';
    const QUEDAS_GRAFICO = 'quedas-grafico';
    const INATIVIDADE_GRAFICO = "inatividade-grafico";

    const [dataDiaIni, setDataDiaIni] = useState<string>( dataToString( new Date() ) );
    const [dataDiaFim, setDataDiaFim] = useState<string>( dataToString( new Date() ) );
    const [pacotesDados, setPacotesDados] = useState<unknown[]>( [] );
    const [quedasDados, setQuedasDados] = useState<unknown[]>( [] );
    const [inatividadesDados, setInatividadesDados] = useState<unknown[]>( [] );

    const [activeGrafico, setActiveGrafico] = useState( PACOTES_GRAFICO );

    const {
        listEventosByIntervalo,
        loading,
        errorMessage,
        infoMessage
    } = useListEventosByDiaViewModel();

    const { dispositivoId } = useParams();

    const onLoadEventos = async () => {
        try {
            const did : number = parseInt( dispositivoId! );
            const eventos : EventoResponse[] = await listEventosByIntervalo( did, dataDiaIni, dataDiaFim );
           
            const pacotesDadosList = [];
            const quedasDadosList = [];
            const inatividadeDadosList = [];

            let quantEventosByGrupo;
            if ( eventos.length < DADOS_QUANT )
                quantEventosByGrupo = eventos.length;
            else quantEventosByGrupo = Math.floor( eventos.length / DADOS_QUANT );
            
            for( let i = 0; i < eventos.length; i++ ) {
                if ( i % quantEventosByGrupo == 0 ) {
                    pacotesDadosList.push( { name: '', sucessos: 0, falhas: 0 } );
                    quedasDadosList.push( { name: '', quedas: 0 } );
                    inatividadeDadosList.push( { name: '', inatividade: 0 } );               
                }

                pacotesDadosList[ pacotesDadosList.length-1 ].sucessos += eventos[ i ].sucessosQuant;
                pacotesDadosList[ pacotesDadosList.length-1 ].falhas += eventos[ i ].falhasQuant;

                quedasDadosList[ quedasDadosList.length-1 ].quedas += eventos[ i ].quedasQuant;
                inatividadeDadosList[ inatividadeDadosList.length-1 ].inatividade += eventos[ i ].tempoInatividade;
                                
                const name = formataDataHoraSemSegundos( eventos[ i ].criadoEm );

                pacotesDadosList[ pacotesDadosList.length-1 ].name = name;
                quedasDadosList[ quedasDadosList.length-1 ].name = name;
                inatividadeDadosList[ inatividadeDadosList.length-1 ].name = name;
            }

            setPacotesDados( pacotesDadosList );
            setQuedasDados( quedasDadosList );
            setInatividadesDados( inatividadeDadosList );
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

                            <div className="d-flex">
                                <AppSpinner className="mx-auto" visible={loading} />
                            </div> 

                            <Button type="button" onClick={onLoadEventos}>
                                Filtrar                        
                                <AppSpinner visible={loading} />
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>

                <div className="estatisticas-grafico-panel mt-3">
                    <Nav variant="tabs" defaultActiveKey={PACOTES_GRAFICO} fill={true}>
                        <Nav.Item>
                            <Nav.Link className="tabbed-item-link" 
                                    eventKey={PACOTES_GRAFICO} 
                                    onClick={ () => setActiveGrafico( PACOTES_GRAFICO ) }>
                                Pacotes
                            </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link className="tabbed-item-link" 
                                    eventKey={QUEDAS_GRAFICO} 
                                    onClick={ () => setActiveGrafico( QUEDAS_GRAFICO ) }>
                                Quedas
                            </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link className="tabbed-item-link" 
                                    eventKey={INATIVIDADE_GRAFICO} 
                                    onClick={ () => setActiveGrafico( INATIVIDADE_GRAFICO ) }>
                                Inatividade
                            </Nav.Link>
                        </Nav.Item>
                    </Nav>

                    { activeGrafico === PACOTES_GRAFICO &&
                        <>
                            <h4 className="text-center mt-3">Estatísticas de pacotes</h4>
                            <h6 className="text-center mb-3">Número de pacotes / Dia e hora</h6>

                            <LineChart className="estatisticas-grafico mx-auto" responsive data={pacotesDados}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="name" type="category" />
                                <YAxis width="auto" type="number" />
                                <Tooltip />
                                <Legend />
                                <Line type="monotone" dataKey="sucessos" stroke="#36F" activeDot={{ r: 8 }} />
                                <Line type="monotone" dataKey="falhas" stroke="#F8B" />
                            </LineChart>                
                        </>
                    }

                    { activeGrafico === QUEDAS_GRAFICO && 
                        <>
                            <h4 className="text-center mt-3">Estatísticas de quedas</h4>
                            <h6 className="text-center mb-3">Número de quedas / Dia e hora</h6>
                            

                            <LineChart className="estatisticas-grafico mx-auto" responsive data={quedasDados}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="name" />
                                <YAxis width="auto" />
                                <Tooltip />
                                <Legend />                    
                                <Line type="monotone" dataKey="quedas" stroke="#F46" />
                            </LineChart>
                        </>
                    }

                   { activeGrafico === INATIVIDADE_GRAFICO && 
                        <>
                            <h4 className="text-center mt-3">Estatísticas de inatividade</h4>
                            <h6 className="text-center mb-3">Tempo de inatividade / Dia e hora</h6>

                            <LineChart className="estatisticas-grafico mx-auto" responsive data={inatividadesDados}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="name" type="category" />
                                <YAxis width="auto" type="number" />
                                <Tooltip />
                                <Legend />
                                <Line type="monotone" dataKey="inatividade" stroke="#4C6" />
                            </LineChart>
                        </>
                   }                    
                </div>
            </div>
        </>
    )
}

export default ShowEventosGraficos;

            /*
            let minCriadoEm = Number.MAX_VALUE;
            let maxCriadoEm = Number.MIN_VALUE;

            for( let i = 0; i < eventos.length; i++ ) {
                if ( eventos[ i ].criadoEm ) {
                    const tempo = new Date( eventos[ i ].criadoEm ).getTime();
                    if ( tempo < minCriadoEm )
                        minCriadoEm = tempo;
                    if ( tempo > maxCriadoEm )
                        maxCriadoEm = tempo;                  
                }
            }

            if ( eventos.length === 0 ) {
                minCriadoEm = 0;
                maxCriadoEm = 0;
            }

            const diffCriadoEm = maxCriadoEm - minCriadoEm;

            const diffAnos = Math.floor( diffCriadoEm / (1000*60*60*24*30*12) );
            const diffMeses = Math.floor( diffCriadoEm / (1000*60*60*24*30) );
            const diffDias = Math.floor( diffCriadoEm / (1000*60*60*24) );
            */

                /*
                let name;
                if ( diffAnos >= 4 ) {
                    name = formataAno( eventos[ i ].criadoEm );
                } else if ( diffAnos >= 1 ) {
                    name = formataAnoMes( eventos[ i ].criadoEm );
                } else if ( diffMeses >= 4 ) {
                    name = formataMesExtenso( eventos[ i ].criadoEm );
                } else if ( diffMeses >= 1 ) {
                    name = formataMesDia( eventos[ i ].criadoEm );
                } else if ( diffDias >= 4 ) {
                    name = formataDia( eventos[ i ].criadoEm );
                } else if ( diffDias >= 1 ) {
                    name = formataDiaHora( eventos[ i ].criadoEm );
                } else {
                    name = formataHora( eventos[ i ].criadoEm );
                }
                */