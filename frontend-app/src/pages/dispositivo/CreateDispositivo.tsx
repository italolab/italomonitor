import { useEffect, useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveDispositivoViewModel from "../../viewModel/dispositivo/useSaveDispositivoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import type { SaveDispositivoRequest } from "../../model/dto/request/SaveDispositivoRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import type { EmpresaResponse } from "../../model/dto/response/EmpresaResponse";

function CreateDispositivo() {

    const [host, setHost] = useState<string>( '' );
    const [nome, setNome] = useState<string>( '' );
    const [descricao, setDescricao] = useState<string>( '' );
    const [localizacao, setLocalizacao] = useState<string>( '' );
    const [empresaId, setEmpresaId] = useState<number>( -1 );
    const [empresas, setEmpresas] = useState<EmpresaResponse[]>( [] );
    

    const {
        createDispositivo,
        getEmpresas,
        loading,
        errorMessage,
        infoMessage
    } = useSaveDispositivoViewModel();

    const navigate = useNavigate();

    useEffect( () => {
        loadData();
    }, [] );

    const loadData = async () => {
        try {
            const empresasList = await getEmpresas();
            setEmpresas( empresasList );
            if ( empresasList.length > 0 )
                setEmpresaId( empresasList[ 0 ].id );            
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
                empresaId : empresaId
            }

            await createDispositivo( dispositivo );
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
                        <h3 className="text-center m-0">Registro de dispositivos</h3>
                    </Card.Header>
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

                            <Form.Group controlId="empresa">
                                <Form.Label>Empresa</Form.Label>
                                <Form.Select className="mb-3"
                                        value={empresaId} 
                                        onChange={(e) => setEmpresaId( parseInt( e.target.value ) )}>
                                    {empresas.map( (emp, index) => 
                                        <option key={index} value={emp.id}>
                                            {emp.nome}
                                        </option>
                                    )}
                                </Form.Select>
                            </Form.Group>

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