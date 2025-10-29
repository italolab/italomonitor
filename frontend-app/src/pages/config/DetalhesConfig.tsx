import { useNavigate } from "react-router-dom";
import useDetalhesConfigViewModel from "../../viewModel/config/useDetalhesConfigViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../util/useEffectOnce";

function DetalhesConfig() {

    const {
        loadConfig,
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

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25}/> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( '/update-config' )} className="func">
                    <MdOutlineEdit size={25}/> Editar configurações
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

                        <AppSpinner className="mx-auto" visible={loading} />
                        
                        <AppField name="número de pacotes por lote">
                            {config.numPacotesPorLote}
                        </AppField>                  
                        <AppField name="delay de monitoramento">
                            {config.monitoramentoDelay}
                        </AppField>      
                        <AppField name="período de registro de evento">
                            {config.registroEventoPeriodo}
                        </AppField>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesConfig;