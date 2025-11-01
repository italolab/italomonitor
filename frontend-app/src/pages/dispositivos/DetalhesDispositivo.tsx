import { useNavigate, useParams } from "react-router-dom";
import useDetalhesDispositivoViewModel from "../../core/viewModel/dispositivo/useDetalhesDispositivoViewModel";
import { useContext, useEffect, useRef } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdEvent, MdOutlineEdit, MdPlayCircle, MdStopCircle } from "react-icons/md";
import AppBoxInfo from "../../components/AppBoxInfo";

import { AuthContext } from "../../context/AuthProvider";

function DetalhesDispositivo() {

    const effectCalled = useRef( false );
    const deactivateFunc = useRef( () => {} );

    const {accessToken} = useContext(AuthContext);

    const {
        loadDispositivo,
        startMonitoramento,
        stopMonitoramento,
        websocketConnect,
        dispositivo,
        loading,
        errorMessage,
        infoMessage,
        setErrorMessage
    } = useDetalhesDispositivoViewModel();

    const { dispositivoId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        if ( effectCalled.current === false ) {
            if ( accessToken === null || accessToken === '' ) {
                setErrorMessage( 'Página atualizada após o login. Para funcionar a atualização de dados em tempo real, é necessário logar novamente sem atualizar a página.' );
            } else {
                deactivateFunc.current = websocketConnect();
            }

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