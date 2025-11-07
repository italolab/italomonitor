import { useNavigate, useParams } from "react-router-dom";
import useDetalhesEmpresaViewModel from "../../core/viewModel/empresa/useDetalhesEmpresaViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";
import EmpresaInfoBox from "./EmpresaInfoBox";

function NoAdminDetalhesEmpresa() {

    const {
        loadEmpresa,
        empresa,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesEmpresaViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } )

    const onLoad = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await loadEmpresa( eid );
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
                <Button type="button" onClick={() => navigate( `/no-admin-update-empresa/${empresaId}` )} className="func">
                    <MdOutlineEdit size={25}/> Editar seus dados
                </Button>                
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <h3 className="m-0 text-center">Dados de {empresa.nome}</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
                        <EmpresaInfoBox empresa={empresa} />
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default NoAdminDetalhesEmpresa;