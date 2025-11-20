import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveDispositivoViewModel from "../../core/viewModel/dispositivo/useSaveDispositivoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveDispositivoRequest } from "../../core/model/dto/request/SaveDispositivoRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate, useParams } from "react-router-dom";
import type { AgenteResponse } from "../../core/model/dto/response/AgenteResponse";
import useEffectOnce from "../../core/util/useEffectOnce";

function CreateDispositivo() {

    const [host, setHost] = useState<string>( '' );
    const [nome, setNome] = useState<string>( '' );
    const [descricao, setDescricao] = useState<string>( '' );
    const [localizacao, setLocalizacao] = useState<string>( '' );    
    const [monitoradoPorAgente, setMonitoradoPorAgente] = useState<boolean>( false );

    const [agentes, setAgentes] = useState<AgenteResponse[]>( [] );
    const [agenteId, setAgenteId] = useState<number>( -1 );

    const {
        createDispositivo,
        getAgentesDaEmpresa,
        loading,
        errorMessage,
        infoMessage
    } = useSaveDispositivoViewModel();

    const { empresaId } = useParams(); 

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            const agentesList : AgenteResponse[] = await getAgentesDaEmpresa( eid );

            if ( agentesList.length > 0 )
                setAgenteId( agentesList[ 0 ].id );

            setAgentes( agentesList );
        } catch ( error ) {
            console.error( error );
        }
    };

    const onSave = async () => {
        try {
            const dispositivo : SaveDispositivoRequest = {
                host : host,
                nome : nome,
                descricao : descricao,
                localizacao : localizacao,
                monitoradoPorAgente: monitoradoPorAgente,
                empresaId : parseInt( empresaId! ),
                agenteId : agenteId
            }

            await createDispositivo( dispositivo );

            setHost( '' );
            setNome( '' );
            setDescricao( '' );
            setLocalizacao( '' );
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <h3 className="title">Registro de dispositivos</h3>

            <div className="d-flex justify-content-center mt-3">
                <Card className="mx-auto">                    
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="host">
                                <Form.Label>Host</Form.Label>
                                <Form.Control type="text"
                                    value={host}
                                    onChange={ ( e ) => setHost( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="nome">
                                <Form.Label>Nome</Form.Label>
                                <Form.Control type="text"
                                    value={nome}
                                    onChange={ ( e ) => setNome( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="descricao">
                                <Form.Label>Descrição</Form.Label>
                                <Form.Control as="textarea" rows={4}
                                    value={descricao}
                                    onChange={ ( e ) => setDescricao( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="localizacao">
                                <Form.Label>Localização</Form.Label>
                                <Form.Control as="textarea" rows={4}
                                    value={localizacao}
                                    onChange={ ( e ) => setLocalizacao( e.target.value ) } />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="monitoradoPorAgente">
                                <Form.Check type="checkbox" 
                                        label="Monitorado por agente"
                                        checked={monitoradoPorAgente}
                                        onChange={ () => setMonitoradoPorAgente( !monitoradoPorAgente )}
                                        inline />
                            </Form.Group>

                            { monitoradoPorAgente === true && 
                                <Form.Group controlId="agente">
                                    <Form.Label>Agente</Form.Label>
                                    <Form.Select className="mb-3"
                                            value={agenteId} 
                                            onChange={(e) => setAgenteId( parseInt( e.target.value ) )}>
                                        { agentes.map( (agente, index) => 
                                            <option key={index} value={agente.id}>
                                                {agente.nome}
                                            </option>
                                        ) }
                                    </Form.Select>
                                </Form.Group>
                            }
                           
                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

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

export default CreateDispositivo;