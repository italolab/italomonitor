import { useState } from "react";
import { Button, Card, Form } from "react-bootstrap";
import useSaveDispositivoViewModel from "../../core/viewModel/dispositivo/useSaveDispositivoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";

import { useNavigate, useParams } from "react-router-dom";
import type { SaveDispositivoRequest } from "../../core/model/dto/request/SaveDispositivoRequest";
import AppLayout from "../../layout/AppLayout";
import { MdArrowBack } from "react-icons/md";
import type { EmpresaResponse } from "../../core/model/dto/response/EmpresaResponse";
import useEffectOnce from "../../core/util/useEffectOnce";

function UpdateDispositivo() {

    const [host, setHost] = useState<string>( '' );
    const [nome, setNome] = useState<string>( '' );
    const [descricao, setDescricao] = useState<string>( '' );
    const [localizacao, setLocalizacao] = useState<string>( '' );
    const [empresaId, setEmpresaId] = useState<number>( -1 );
    const [empresas, setEmpresas] = useState<EmpresaResponse[]>( [] );

    const {
        updateDispositivo,
        getDispositivo,
        getEmpresas,
        loading,
        errorMessage,
        infoMessage
    } = useSaveDispositivoViewModel();

    const { dispositivoId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoadDispositivo();
    } );

    const onLoadDispositivo = async () => {
        try {
            const uid : number = parseInt( dispositivoId! );
            const dispositivo = await getDispositivo( uid );
            setHost( dispositivo.host );
            setNome( dispositivo.nome );
            setDescricao( dispositivo.descricao );
            setLocalizacao( dispositivo.localizacao );
            setEmpresaId( dispositivo.empresa.id );

            const empresasList = await getEmpresas();
            setEmpresas( empresasList );
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
            };
           
            const uid : number = parseInt( dispositivoId! );
            await updateDispositivo( uid, dispositivo );            
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
                        <h3 className="text-center m-0">Alteração de dispositivos</h3>
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
                                    <option value={-1}>Nenhuma empresa</option>
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

export default UpdateDispositivo;