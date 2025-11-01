import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveMonitorServerViewModel from "../../core/viewModel/monitorServer/useSaveMonitorServerViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveMonitorServerRequest } from "../../core/model/dto/request/SaveMonitorServerRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";

function UpdateMonitorServer() {

    const [host, setHost] = useState<string>( '' );

    const {
        updateMonitorServer,
        getMonitorServer,
        loading,
        errorMessage,
        infoMessage
    } = useSaveMonitorServerViewModel();

    const { monitorServerId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadMonitorServer();
    } );

    const onLoadMonitorServer = async () => {
        try {
            const msid : number = parseInt( monitorServerId! );
            const monitorServer = await getMonitorServer( msid );
            setHost( monitorServer.host );

        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const monitorServer : SaveMonitorServerRequest = {
                host : host
            };
           
            const msid : number = parseInt( monitorServerId! );
            await updateMonitorServer( msid, monitorServer );            
        } catch ( error ) {
            console.error( error );
        }
    };


    return (
        <AppLayout>
            <div className="d-flex justify-content-start">
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">
                    <Card.Header>
                        <h3 className="text-center m-0">Alteração de servidor de monitoramento</h3>
                    </Card.Header>
                    <Card.Body>
                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>

                        <Form>                                                                                
                            <Form.Group className="mb-3" controlId="host">
                                <Form.Label>Host</Form.Label>
                                <Form.Control type="text"
                                    placeholder="Informe o host"
                                    value={host}
                                    onChange={ ( e ) => setHost( e.target.value ) } />
                            </Form.Group>

                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            <div className="d-flex">
                                <AppSpinner className="mx-auto" visible={loading} />
                            </div>                                                    

                            <Button type="button" onClick={onSave}>
                                Salvar 
                                <AppSpinner visible={loading} />
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    );
}

export default UpdateMonitorServer;