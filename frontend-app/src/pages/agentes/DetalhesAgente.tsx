import { useNavigate, useParams } from "react-router-dom";
import useDetalhesAgenteViewModel from "../../core/viewModel/agente/useDetalhesAgenteViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";
import { formataDataHora } from "../../core/util/sistema-util";

function DetalhesAgente() {

    const {
        loadAgente,
        agente,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesAgenteViewModel();

    const { agenteId, empresaId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } )

    const onLoad = async () => {
        try {
            const rid : number = parseInt( agenteId! );
            await loadAgente( rid );
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
                <Button type="button" onClick={() => navigate( `/update-agente/${agenteId}/${empresaId}`)} className="func">
                    <MdOutlineEdit size={25}/> Editar agente
                </Button>
            </div>
            
            <h3 className="title">Detalhes do agente</h3>
           
            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>

                        <span className="fs-3">
                            <AppField name="estado atualizado em">
                                {formataDataHora( agente.ultimoEnvioDeEstadoEm )}
                            </AppField>
                        </span>
                        
                        <AppField name="ID">
                            {agente.id}
                        </AppField>
                        <AppField name="chave">
                            {agente.chave}
                        </AppField>  
                        <AppField name="nome">
                            {agente.nome}
                        </AppField>                           
                        <AppField name="quantidade de dispositivos">
                            {agente.dispositivosQuant}
                        </AppField>                     
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesAgente;