import { useNavigate, useParams } from "react-router-dom";
import useDetalhesUsuarioGrupoViewModel from "../../core/viewModel/usuarioGrupo/useDetalhesUsuarioGrupoViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdLink, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";

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

    useEffectOnce( () => {
        onLoad();
    } )

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
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="func">
                    <MdArrowBack size={25}/> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/update-usuario-grupo/${usuarioGrupoId}`)} className="func">
                    <MdOutlineEdit size={25}/> Editar grupo
                </Button>
                <Button type="button" onClick={() => navigate( `/vincular-role/${usuarioGrupoId}`)} className="func">
                    <MdLink size={25} /> Vincular role
                </Button>
            </div>
            
            <h3 className="title">detlhes do grupo</h3>
           
            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
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