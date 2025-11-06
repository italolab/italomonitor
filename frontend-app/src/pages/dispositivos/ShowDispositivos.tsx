import { useEffect, useRef, useState } from "react";
import { Button, Card, Col, Form, Row } from "react-bootstrap";
import useShowDispositivosViewModel from "../../core/viewModel/dispositivo/useShowDispositivosViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { Link, useNavigate, useParams } from "react-router-dom";
import { MdAdd, MdArrowBack, MdPlayCircle, MdStopCircle } from "react-icons/md";

import './style/ManterDispositivos.css'
import { LuInfo } from "react-icons/lu";

function ShowDispositivos() {

    const [searchTermo, setSearchTermo] = useState<string>( '' );

    const effectCalled = useRef( false );
    const deactivateFunc = useRef( () => {} );

    const { 
        websocketConnect,
        loadInfos,
        loadDispositivos,
        filterDispositivos, 
        startAllMonitoramentos,
        stopAllMonitoramentos,
        dispositivosFiltrados,
        empresa,
        loading,
        errorMessage, 
        infoMessage,
    } = useShowDispositivosViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        if ( effectCalled.current === false ) {
            ( async () => {
                deactivateFunc.current = await websocketConnect();
            } )();

            onLoad();

            effectCalled.current = true;
        }

        return () => {
            deactivateFunc.current();                
        };
    }, [] );


    const onLoad = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await loadInfos( eid );
            await loadDispositivos( eid ); 
        } catch ( error ) {
            console.error( error );
        }
    };

    const onFilter = async () => {
        try {
            await filterDispositivos( searchTermo );
        } catch ( error ) {
            console.log( error );
        }
    };

    const onStartAllMonitoramentos = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await startAllMonitoramentos( eid );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onStopAllMonitoramentos = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await stopAllMonitoramentos( eid );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>        
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25} /> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/create-dispositivo/${empresaId}`)} className="func">
                    <MdAdd size={25}/> Novo dispositivo
                </Button>
                <Button type="button" onClick={onStartAllMonitoramentos} className="func">
                    <MdPlayCircle size={25} /> Iniciar todos os monitoramentos
                </Button>
                <Button type="button" onClick={onStopAllMonitoramentos} className="func">
                    <MdStopCircle size={25} /> Encerrar todos os monitoramentos
                </Button>
            </div>

            <h3 className="title">Dispositivos de {empresa.nome}</h3>

            <AppMessage message={errorMessage} type="error" />
            <AppMessage message={infoMessage} type="info" />

            <div className="d-flex">
                <AppSpinner className="mx-auto" visible={loading} showEsp={false}/>
            </div>

            <div className="d-block w-100 mt-3 d-flex justify-content-center">
                <Card>
                    <Card.Header>
                        <h5 className="my-2">Campos do filtro</h5>
                    </Card.Header>
                    <Card.Body className="p-3">
                        <Form>
                            <Form.Group className="mb-3" controlId="searchTermo">
                                <Form.Label>Termo para busca</Form.Label>
                                <Form.Control type="text" 
                                    placeholder="Informe o termo para busca"
                                    value={searchTermo}
                                    onChange={ (e) => setSearchTermo( e.target.value ) }
                                    onKeyDown={ onFilter } />
                            </Form.Group>                            
                        </Form>
                    </Card.Body>
                </Card>
            </div>
                                
            <div className="d-flex justify-content-center mt-3">                
                <Row className="disp-row w-100">
                    { dispositivosFiltrados.map( (dispositivo, index) =>                 
                        <Col key={index} sm={4} className="disp-col">
                            <div className="disp-card">
                                <div className="d-flex align-items-center justify-content-between">
                                    <div>
                                        <h3 className="d-inline-block fw-bold rounded-2 bg-dark px-2 py-1">
                                            {dispositivo.id}
                                        </h3>
                                        <span className="mx-2"></span>
                                        {dispositivo.status === 'ATIVO' 
                                            ? <h3 className="d-inline-block text-light"><span>Ativo</span></h3> 
                                            : <h3 className="d-inline-block text-warning"><span>Inativo</span></h3>
                                        }
                                    </div>
                                    <span className="p-2 rounded-3 bg-white text-dark">
                                        <Link to={`/detalhes-dispositivo/${dispositivo.id}`}>
                                            <LuInfo color="green" size={20}/>
                                        </Link>                                        
                                    </span>
                                </div>                                
                                <h3 className="mb-3">{dispositivo.nome}</h3>
                                <div>{dispositivo.localizacao}</div>                            
                            </div>
                        </Col> 
                    )}
                </Row>
            </div>                   
        </AppLayout>
    );
}

export default ShowDispositivos;