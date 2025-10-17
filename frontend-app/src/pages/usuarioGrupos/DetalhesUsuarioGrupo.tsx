import { useNavigate, useParams } from "react-router-dom";
import useDetalhesUsuarioGrupoViewModel from "../../viewModel/usuarioGrupo/useDetalhesUsuarioGrupoViewModel";
import { useEffect } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdLink, MdOutlineEdit } from "react-icons/md";

function DetalhesUsuarioGrupo() {

    const {
        loadUsuarioGrupo,
        usuarioGrupo,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesUsuarioGrupoViewModel();

    const { usuarioGrupoId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        onLoad();
    }, [] )

    const onLoad = async () => {
        try {
            const gid : number = parseInt( usuarioGrupoId! );
            await loadUsuarioGrupo( gid );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <div className="d-flex justify-content-between">
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>
                <div>
                    <Button type="button" onClick={() => navigate( `/update-usuario-grupo/${usuarioGrupoId}`)} className="d-inline-flex align-items-center mx-3">
                        <MdOutlineEdit size={25}/> Editar grupo
                    </Button>
                    <Button type="button" onClick={() => navigate( `/vincular-role/${usuarioGrupoId}`)} className="d-inline-flex align-items-center">
                        <MdLink size={25} /> Vincular role
                    </Button>
                </div>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card style={{width: '30em'}}>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do grupo</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <AppSpinner className="mx-auto" visible={loading} />

                        <AppField name="ID">
                            {usuarioGrupo.id}
                        </AppField>
                        <AppField name="nome">
                            {usuarioGrupo.nome}
                        </AppField>                        
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesUsuarioGrupo;