import { Card } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useUsuarioDashboardViewModel from "../../core/viewModel/dashboard/useUsuarioDashboardViewModel";
import useEffectOnce from "../../core/util/useEffectOnce";
import EmpresaInfoBox from "../empresas/EmpresaInfoBox";

function UsuarioDashboard() {

    const {
        load,
        empresa,
        dispositivosInfos,
        errorMessage,
        infoMessage,
        loading,
        setErrorMessage
    } = useUsuarioDashboardViewModel();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        const empresaId = localStorage.getItem( 'empresaId' );
        if ( Number.isNaN( empresaId ) ) {
            setErrorMessage( 'ID de empresa não armazenado no login.' );
            return;
        }

        try {            
            const eid : number = parseInt( empresaId! );
            await load( eid );
        } catch ( error ) {
            console.log( error );
        }
    };

    return (
        <div className="d-flex justify-content-center mt-3">
            <Card>
                <Card.Header>
                    <h3 className="m-0 text-center">Dashboard</h3>
                </Card.Header>
                <Card.Body>
                    <AppMessage message={errorMessage} type="error" />
                    <AppMessage message={infoMessage} type="info" />

                    <div className="d-flex">
                        <AppSpinner className="mx-auto" visible={loading} />
                    </div>
                    
                    <h6 className="text-center p-2 bg-light border">
                        {dispositivosInfos.quantTotal} dispositivos no total, &nbsp;                        
                        {dispositivosInfos.sendoMonitoradosQuant} sendo monitorados.                
                    </h6>

                    <EmpresaInfoBox empresa={empresa} />                                           
                </Card.Body>
            </Card>
        </div>
    )
}

export default UsuarioDashboard;