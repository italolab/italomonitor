import { useNavigate, useParams } from "react-router-dom";
import useDetalhesMonitorServerViewModel from "../../core/viewModel/monitorServer/useDetalhesMonitorServerViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";

function DetalhesMonitorServer() {

    const {
        loadMonitorServer,
        monitorServer,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesMonitorServerViewModel();

    const { monitorServerId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } )

    const onLoad = async () => {
        try {
            const msid : number = parseInt( monitorServerId! );
            await loadMonitorServer( msid );
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
                <Button type="button" onClick={() => navigate( `/update-monitor-server/${monitorServerId}`)} className="func">
                    <MdOutlineEdit size={25}/> Editar servidor
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do servidor de monitoramento</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
                        <AppField name="id">
                            {monitorServer.id}
                        </AppField>
                        <AppField name="host">
                            {monitorServer.host}
                        </AppField>                        
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesMonitorServer;