import { useNavigate } from "react-router-dom";
import useDetalhesConfigViewModel from "../../core/viewModel/config/useDetalhesConfigViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit, MdRestartAlt } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";
import { FaServer } from "react-icons/fa";
import ConfigInfoBox from "./ConfigInfoBox";

function DetalhesConfig() {

    const {
        loadConfig,
        startAllMonitoramentos,
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

    const onStartOrRestartMonitoramentos = async () => {
        try {
            await startAllMonitoramentos();
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
                <Button type="button" onClick={onStartOrRestartMonitoramentos} className="func">
                    <MdRestartAlt size={25}/> Startar ou restartar monitoramentos
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do configurações</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
                        <ConfigInfoBox config={config} />

                        <Button type="button" onClick={() => navigate( '/monitor-servers' )} className="func mt-3">
                            <FaServer size={25}/> &nbsp; Servidores de monitoramento
                        </Button>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesConfig;