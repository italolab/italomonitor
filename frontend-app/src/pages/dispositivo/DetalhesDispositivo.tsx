import { useNavigate, useParams } from "react-router-dom";
import useDetalhesDispositivoViewModel from "../../viewModel/dispositivo/useDetalhesDispositivoViewModel";
import { useEffect } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";
import AppBoxInfo from "../../components/AppBoxInfo";

function DetalhesDispositivo() {

    const {
        loadDispositivo,
        dispositivo,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesDispositivoViewModel();

    const { dispositivoId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        onLoad();
    }, [] )

    const onLoad = async () => {
        try {
            const uid : number = parseInt( dispositivoId! );
            await loadDispositivo( uid );
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