import { useNavigate, useParams } from "react-router-dom";
import useDetalhesUsuarioViewModel from "../../viewModel/usuario/useDetalhesUsuarioViewModel";
import { useEffect } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdLink, MdOutlineEdit } from "react-icons/md";

function DetalhesUsuario() {

    const {
        loadUsuario,
        usuario,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesUsuarioViewModel();

    const { usuarioId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        onLoad();
    }, [] )

    const onLoad = async () => {
        try {
            const uid : number = parseInt( usuarioId! );
            await loadUsuario( uid );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <div className="d-flex justify-content-between">
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25} /> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/update-usuario/${usuarioId}`)} className="d-inline-flex align-items-center">
                    <MdOutlineEdit size={25} /> Editar usuário
                </Button>
                <Button type="button" onClick={() => navigate( `/vincular-usuario-grupo/${usuarioId}`)} className="d-inline-flex align-items-center">
                    <MdLink size={25} /> Vincular grupo
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card style={{width: '30em'}}>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do usuário</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <AppSpinner className="mx-auto" visible={loading} />

                        <AppField name="ID">
                            {usuario.id}
                        </AppField>
                        <AppField name="nome">
                            {usuario.nome}
                        </AppField>
                        <AppField name="email">
                            {usuario.email}
                        </AppField>
                        <AppField name="username">
                            {usuario.username}
                        </AppField>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesUsuario;