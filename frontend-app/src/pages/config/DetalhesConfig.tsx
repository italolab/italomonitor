import { useNavigate } from "react-router-dom";
import useDetalhesConfigViewModel from "../../core/viewModel/config/useDetalhesConfigViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit, MdPlayCircle, MdStopCircle } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";
import { FaEdit } from "react-icons/fa";
import ConfigInfoBox from "./ConfigInfoBox";

function DetalhesConfig() {

    const {
        loadConfig,
        startAllMonitoramentos,
        stopAllMonitoramentos,
        config,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesConfigViewModel();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } )

    const onLoad = async () => {
        try {
            await loadConfig();
        } catch ( error ) {
            console.error( error );
        }
    };

    const onStartAllMonitoramentos = async () => {
        try {
            await startAllMonitoramentos();
        } catch ( error ) {
            console.error( error );
        }
    };

    const onStopAllMonitoramentos = async () => {
        try {
            await stopAllMonitoramentos();
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25}/> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( '/update-config' )} className="func">
                    <MdOutlineEdit size={25}/> Editar configurações
                </Button>
                <Button type="button" onClick={onStartAllMonitoramentos} className="func">
                    <MdPlayCircle size={25}/> Startar monitoramentos
                </Button>
                <Button type="button" onClick={onStopAllMonitoramentos} className="func">
                    <MdStopCircle size={25}/> Parar monitoramentos
                </Button>
            </div>
            
            <h3 className="title">Detalhes de configurações</h3>
       
            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
                        <ConfigInfoBox config={config} />

                        <Button type="button" onClick={() => navigate( '/monitor-servers' )} className="func mt-3">
                            <FaEdit size={25}/> &nbsp; Editar servidores de monitoramento
                        </Button>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesConfig;