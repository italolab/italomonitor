import { Card } from "react-bootstrap";
import useEffectOnce from "../../core/util/useEffectOnce";
import useAdminDashboardViewModel from "../../core/viewModel/dashboard/useAdminDashboardViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import ConfigInfoBox from "../config/ConfigInfoBox";

function AdminDashboard() {

    const {
        load,
        config,
        errorMessage,
        infoMessage,
        loading
    } = useAdminDashboardViewModel();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        try {
            await load();
        } catch ( error ) {
            console.error( error );
        }
    };  

    return (
        <>                     
            <h3 className="title">Dashboard</h3>

            <div className="d-flex justify-content-center mt-3">
                <Card>                    
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                        
                        <ConfigInfoBox config={config} />                       
                    </Card.Body>
                </Card>
            </div>
        </>
    )
}

export default AdminDashboard;