import { useNavigate, useParams } from "react-router-dom";
import useDetalhesEmpresaViewModel from "../../viewModel/empresa/useDetalhesEmpresaViewModel";
import { useEffect } from "react";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppField from "../../components/AppField";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdOutlineEdit } from "react-icons/md";

function DetalhesEmpresa() {

    const {
        loadEmpresa,
        empresa,
        loading,
        errorMessage,
        infoMessage
    } = useDetalhesEmpresaViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    useEffect( () => {
        onLoad();
    }, [] )

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
            <div className="d-flex justify-content-between">
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>
                <Button type="button" onClick={() => navigate( `/update-empresa/${empresaId}`)} className="d-inline-flex align-items-center">
                    <MdOutlineEdit size={25}/> Editar empresa
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card style={{width: '30em'}}>
                    <Card.Header>
                        <h3 className="m-0 text-center">Detalhes do empresa</h3>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <AppSpinner className="mx-auto" visible={loading} />

                        <AppField name="ID">
                            {empresa.id}
                        </AppField>
                        <AppField name="nome">
                            {empresa.nome}
                        </AppField>                        
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default DetalhesEmpresa;