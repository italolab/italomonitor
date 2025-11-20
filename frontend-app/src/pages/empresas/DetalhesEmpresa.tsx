import { useNavigate, useParams } from "react-router-dom";
import useDetalhesEmpresaViewModel from "../../core/viewModel/empresa/useDetalhesEmpresaViewModel";
import AppLayout from "../../layout/AppLayout";
import { Button, Card } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { MdArrowBack, MdDeviceHub, MdOutlineEdit } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";
import EmpresaInfoBox from "./EmpresaInfoBox";
import { FaMoneyBill1 } from "react-icons/fa6";
import { LuRouter } from "react-icons/lu";

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
                <Button type="button" onClick={() => navigate( `/update-empresa/${empresaId}` )} className="func">
                    <MdOutlineEdit size={25}/> Editar empresa
                </Button>
                <Button type="button" onClick={() => navigate( `/pagamentos/${empresaId}` )} className="func">
                    <FaMoneyBill1 size={25}/> &nbsp; Ver pagamentos
                </Button>
                <Button type="button" onClick={() => navigate( `/dispositivos/${empresaId}`)} className="func">
                    <LuRouter size={25}/> &nbsp; Ver dispositivos
                </Button>
                <Button type="button" onClick={() => navigate( `/agentes/${empresaId}`)} className="func">
                    <MdDeviceHub size={25}/> Ver agente
                </Button>
            </div>
            
            <h3 className="title">Detalhes da empresa</h3>
            
            <div className="d-flex justify-content-center mt-3">
                <Card>
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

export default DetalhesEmpresa;