import { useNavigate, useParams } from "react-router-dom";
import useDetalhesRoleViewModel from "../../viewModel/role/useDetalhesRoleViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../viewModel/useEffectOnce";

function DetalhesRole() {

    const {
        loadRole,
        role,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesRoleViewModel();

    const { roleId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } )

    const onLoad = async () => {
        try {
            const rid : number = parseInt( roleId! );
            await loadRole( rid );
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
                <Button type="button" onClick={() => navigate( `/update-role/${roleId}`)} className="func">
                    <MdOutlineEdit size={25}/> Editar role
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do role</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <AppSpinner className="mx-auto" visible={loading} />

                        <AppField name="ID">
                            {role.id}
                        </AppField>
                        <AppField name="nome">
                            {role.nome}
                        </AppField>                        
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesRole;