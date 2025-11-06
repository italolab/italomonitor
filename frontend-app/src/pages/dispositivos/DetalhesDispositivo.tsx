import { useNavigate, useParams } from "react-router-dom";
import useDetalhesDispositivoViewModel from "../../core/viewModel/dispositivo/useDetalhesDispositivoViewModel";
import { useEffect, useRef, useState } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card, Modal } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdDeleteForever, MdEvent, MdOutlineEdit, MdPlayCircle, MdStopCircle } from "react-icons/md";
import AppBoxInfo from "../../components/AppBoxInfo";

function DetalhesDispositivo() {

    const [removeModalVisible, setRemoveModalVisible] = useState<boolean>( false );

    const effectCalled = useRef( false );
    const deactivateFunc = useRef( () => {} );

    const {
        loadDispositivo,
        removeDispositivo,
        startMonitoramento,
        stopMonitoramento,
        websocketConnect,
        dispositivo,
        loading,
        errorMessage,
        infoMessage,
    } = useDetalhesDispositivoViewModel();

    const { dispositivoId } = useParams();

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
            const devId : number = parseInt( dispositivoId! );
            await loadDispositivo( devId );
        } catch ( error ) {
            console.error( error );
        }
    };
    
    const onConfirmRemover = async () => {
        setRemoveModalVisible( true );
    };

    const onRemover = async () => {
        setRemoveModalVisible( false );
        try {
            const did : number = parseInt( dispositivoId! );
            await removeDispositivo( did );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onStartMonitoramento = async () => {
        try {
            const devId : number = parseInt( dispositivoId! );
            await startMonitoramento( devId );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onStopMonitoramento = async () => {
        try {
            const devId : number = parseInt( dispositivoId! );
            await stopMonitoramento( devId );
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
                    {dispositivo.host} - {dispositivo.nome} ?
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
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25} /> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/update-dispositivo/${dispositivoId}`)} className="func">
                    <MdOutlineEdit size={25} /> Editar dispositivo
                </Button>
                <Button type="button" variant="danger" onClick={onConfirmRemover} className="func text-white">
                    <MdDeleteForever size={25} /> Remover dispositivo
                </Button>
                <Button type="button" onClick={() => navigate( `/infos-eventos/${dispositivoId}`)} className="func">
                    <MdEvent size={25} /> Eventos
                </Button>
                <Button type="button" onClick={onStartMonitoramento} className="func">
                    <MdPlayCircle size={25} /> Iniciar monitoramento
                </Button>
                <Button type="button" onClick={onStopMonitoramento} className="func">
                    <MdStopCircle size={25} /> Encerrar monitoramento
                </Button>                
            </div>

            <div className="d-flex justify-content-center mt-2">
                <Card>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do dispositivo</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />
                        
                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>

                        <div className="d-flex align-items-center justify-content-center">
                            <AppBoxInfo name="status">
                                { dispositivo.status == 'ATIVO' ? 
                                    <span className="text-blue">Ativo</span> : 
                                    <span className="text-danger">Inativo</span>
                                }
                            </AppBoxInfo>
                            <AppBoxInfo name="sendo monitorado">
                                { dispositivo.sendoMonitorado == true ? 
                                    <span className="text-blue">Sim</span> : 
                                    <span className="text-dark">Não</span>                                
                                }
                            </AppBoxInfo>
                        </div>

                        <br />                       

                        <AppField name="ID">
                            {dispositivo.id}
                        </AppField>
                        <AppField name="host">
                            {dispositivo.host}
                        </AppField>
                        <AppField name="nome">
                            {dispositivo.nome}
                        </AppField>
                        <AppField name="descriçao">
                            {dispositivo.descricao}
                        </AppField>
                        <AppField name="localização">
                            {dispositivo.localizacao}
                        </AppField>
                        <AppField name="empresa">
                            { dispositivo.empresa != null ? dispositivo.empresa.nome : 'Nenhuma empresa!' }
                        </AppField>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesDispositivo;