import { useState } from "react";
import { Button, Card, Col, Container, Form, Row, Table } from "react-bootstrap";
import useManterUsuarioViewModel from "../../viewModel/usuario/useManterUsuarioViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";
import { useNavigate } from "react-router-dom";
import { MdAdd } from "react-icons/md";
import AppOperations from "../../components/AppOperations";

function FilterUsuarios() {

    const [nomepart, setNomepart] = useState<string>( '' );

    const { 
        filterUsuarios, 
        usuarios, 
        loading, 
        errorMessage, 
        infoMessage 
    } = useManterUsuarioViewModel();

    const navigate = useNavigate();

    const onFilter = async () => {
        try {
            await filterUsuarios( nomepart );
        } catch ( error ) {
            console.log( error );
        }
    };

    const onRemover = async () => {

    };

    return (
        <AppLayout>            
            <h3 className="text-center">Funções de usuário</h3>

            <Container fluid>
                <Button type="button" onClick={() => navigate( '/create-usuario')} className="d-flex align-items-center ms-auto">
                    <MdAdd size={25}/> Novo usuário
                </Button>
            </Container>

            <Container fluid className="mt-3">
                <Row>
                    <Col>
                        <Card className="p-3">
                            <Card.Title>
                                <h5>Campos do filtro</h5>
                            </Card.Title>
                            <Form>
                                <Form.Group className="mb-3" controlId="nomepart">
                                    <Form.Label>Nome</Form.Label>
                                    <Form.Control type="text" 
                                        placeholder="Informe parte do nome"
                                        value={nomepart}
                                        onChange={ (e) => setNomepart( e.target.value ) } />
                                </Form.Group>

                                <AppMessage message={errorMessage} type="error" />
                                <AppMessage message={infoMessage} type="info" />

                                <Button type="button" onClick={onFilter}>
                                    Filtrar                        
                                    <AppSpinner visible={loading} />
                                </Button>
                            </Form>
                        </Card>
                    </Col>
                </Row>
            
                <Row className="mt-3">
                    <Col className="overflow-auto">
                        <Table striped bordered hover className="overflow-auto">
                            <thead>
                                <tr className="blue">
                                    <th>ID</th>
                                    <th>Nome</th>
                                    <th>E-Mail</th>
                                    <th>Username</th>
                                    <th>Operações</th>
                                </tr>
                            </thead>
                            <tbody>
                                { usuarios.map( (usuario, index) => 
                                    <tr key={index}>
                                        <td>{usuario.id}</td>
                                        <td>{usuario.nome}</td>
                                        <td>{usuario.email}</td>
                                        <td>{usuario.username}</td>
                                        <td>
                                            <AppOperations 
                                                toDetalhes={`/detalhes-usuario/${usuario.id}`}
                                                toEdit={`/update-usuario/${usuario.id}`} 
                                                onRemover={onRemover} />
                                        </td>
                                    </tr> 
                                )}
                            </tbody>
                        </Table>
                    </Col>
                </Row>
            </Container>
        </AppLayout>
    );
}

export default FilterUsuarios;