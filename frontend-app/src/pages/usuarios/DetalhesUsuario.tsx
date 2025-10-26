import { useNavigate, useParams } from "react-router-dom";
import useDetalhesUsuarioViewModel from "../../viewModel/usuario/useDetalhesUsuarioViewModel";
import AppLayout from "../../layout/AppLayout";
import { Badge, Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdLink, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../viewModel/useEffectOnce";

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

    useEffectOnce( () => {
        onLoad();
    } )

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
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25} /> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/update-usuario/${usuarioId}`)} className="func">
                    <MdOutlineEdit size={25} /> Editar usuário
                </Button>
                <Button type="button" onClick={() => navigate( `/vincular-usuario-grupo/${usuarioId}`)} className="func">
                    <MdLink size={25} /> Editar grupos
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-2">
                <Card>
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
                        <AppField name="empresa">
                            { usuario.empresa != null ? usuario.empresa.nome : 'Nenhuma empresa!' }
                        </AppField>
                        <AppField name="grupos">
                            { usuario.grupos.map( (grupo, index) => 
                                <span>
                                    <Badge bg="primary" key={index} className="my-1">
                                        {grupo.nome}
                                    </Badge>
                                    <span>&nbsp;</span>
                                </span>
                            )}
                        </AppField>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesUsuario;