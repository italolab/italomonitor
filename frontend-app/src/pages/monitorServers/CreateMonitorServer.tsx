import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveMonitorServerViewModel from "../../core/viewModel/monitorServer/useSaveMonitorServerViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveMonitorServerRequest } from "../../core/model/dto/request/SaveMonitorServerRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";

function CreateMonitorServer() {

    const [host, setHost] = useState<string>( '' );

    const {
        createMonitorServer,
        loading,
        errorMessage,
        infoMessage
    } = useSaveMonitorServerViewModel();

    const navigate = useNavigate();

    const onSave = async () => {
        try {
            const monitorServer : SaveMonitorServerRequest = {
                host : host
            }

            await createMonitorServer( monitorServer );
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
                        <h3 className="m-0 text-center">Registro de servidores de monitoramento</h3>
                    </Card.Header>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="host">
                                <Form.Label>Host</Form.Label>
                                <Form.Control type="text"
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

export default CreateMonitorServer;