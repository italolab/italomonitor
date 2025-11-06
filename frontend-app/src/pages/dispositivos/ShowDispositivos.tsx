import { useEffect, useRef, useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useShowDispositivosViewModel from "../../core/viewModel/dispositivo/useShowDispositivosViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { Link, useNavigate, useParams } from "react-router-dom";
import { MdAdd, MdArrowBack, MdNotifications, MdNotificationsOff, MdPlayCircle, MdStopCircle } from "react-icons/md";

import './style/ShowDispositivos.css'
import { LuInfo } from "react-icons/lu";
import { TbRouter, TbRouterOff } from "react-icons/tb";
import { formataDataHora } from "../../core/util/sistema-util";

function ShowDispositivos() {

    const [searchTermo, setSearchTermo] = useState<string>( '' );

    const effectCalled = useRef( false );
    const deactivateFunc = useRef( () => {} );

    const { 
        websocketConnect,
        loadDados,
        filterDispositivos, 
        startEmpresaMonitoramentos,
        stopEmpresaMonitoramentos,
        dispositivosInfos,
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
            await loadDados( eid );
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
            await startEmpresaMonitoramentos( eid );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onStopAllMonitoramentos = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await stopEmpresaMonitoramentos( eid );
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
                                    onKeyUp={ onFilter } />
                            </Form.Group>                            
                        </Form>
                    </Card.Body>
                </Card>
            </div>
                                
            <div className="mt-3 title">
                {dispositivosInfos.quantTotal} dispositivos no total, &nbsp;
                {dispositivosInfos.sendoMonitoradosQuant} sendo monitorados.                
            </div>

            <div className="mt-3">                
                { dispositivosFiltrados.map( (dispositivo, index) =>                 
                    <div key={index} className="disp-card">
                        <div className="d-flex align-items-center justify-content-between">
                            <div>                                        
                                { dispositivo.status === 'ATIVO' 
                                    ? <TbRouter size={20} /> 
                                    : <TbRouterOff size={20} color="yellow" />
                                }
                                <span className="mx-2"></span>
                                { dispositivo.sendoMonitorado === true 
                                    ? <MdNotifications size={20} />
                                    : <MdNotificationsOff size={20} />
                                }
                            </div>
                            <Link to={`/detalhes-dispositivo/${dispositivo.id}`} >
                                <LuInfo color="white" size={20} className="detalhes-over"/>
                            </Link>                                        
                        </div>                          
                        <div>
                            <h1 className="m-0 d-inline-block text-complementar">{dispositivo.latenciaMedia}</h1> 
                            <small> ms</small>
                        </div>
                        <small>{dispositivo.nome}</small>
                        <small className="d-flex justify-content-end">
                            {formataDataHora( dispositivo.stateAtualizadoEm )}
                        </small>
                    </div>
                )}                                
            </div>                   
        </AppLayout>
    );
}

export default ShowDispositivos;