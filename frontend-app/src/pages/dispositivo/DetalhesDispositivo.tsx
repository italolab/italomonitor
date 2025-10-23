import { useNavigate, useParams } from "react-router-dom";
import useDetalhesDispositivoViewModel from "../../viewModel/dispositivo/useDetalhesDispositivoViewModel";
import { useEffect } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit, MdPlayCircle, MdStopCircle } from "react-icons/md";
import AppBoxInfo from "../../components/AppBoxInfo";

import SockJS from 'sockjs-client';
import { Stomp } from "@stomp/stompjs";
import { BASE_WS_URL } from "../../constants/api-constants";

function DetalhesDispositivo() {

    const {
        loadDispositivo,
        startMonitoramento,
        stopMonitoramento,
        dispositivo,
        loading,
        errorMessage,
        infoMessage,
        setDispositivo
    } = useDetalhesDispositivoViewModel();

    const { dispositivoId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        const socket = new SockJS( BASE_WS_URL );
        const stompClient = Stomp.over(socket);

        stompClient.connect( {}, () => {
            stompClient.subscribe('/user/topic/dispositivo', (message) => {
                const data = JSON.parse( message.body );
                setDispositivo( data );                
            } );
        }, (error: unknown) => {
            console.error( error );
        } );

        onLoad();

        return () => {
            if ( stompClient.connected ) {
                stompClient.disconnect( () => {
                    console.log( "Disconectado do servidor de mensagens websocket!" );
                } );
            }
        };
    }, [] )

    const onLoad = async () => {
        try {
            const devId : number = parseInt( dispositivoId! );
            await loadDispositivo( devId );
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
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25} /> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/update-dispositivo/${dispositivoId}`)} className="func">
                    <MdOutlineEdit size={25} /> Editar dispositivo
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

                        <AppSpinner className="mx-auto" visible={loading} />

                        <div className="d-flex align-items-center justify-content-center">
                            <AppBoxInfo name="status">
                                {dispositivo.status == 'ATIVO' ? "Ativo" : "Inativo"}
                            </AppBoxInfo>
                            <AppBoxInfo name="sendo monitorado">
                                {dispositivo.sendoMonitorado == true ? "Sim" : "Não" }
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