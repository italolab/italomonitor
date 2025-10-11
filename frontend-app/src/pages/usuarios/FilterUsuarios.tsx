import { useState } from "react";
import { Button, Card, Form, Table } from "react-bootstrap";
import useFilterUsuarioViewModel from "../../viewModel/usuario/useFilterUsuarioViewModel";
import AppSpinner from "../../components/AppSpinner";
import AppMessage from "../../components/AppMessage";
import AppLayout from "../../layout/AppLayout";

function FilterUsuarios() {

    const [nomepart, setNomepart] = useState<string>( '' );

    const { 
        filterUsuarios, 
        usuarios, 
        loading, 
        errorMessage, 
        infoMessage 
    } = useFilterUsuarioViewModel();

    const filter = async () => {
        try {
            await filterUsuarios( nomepart );
        } catch ( error ) {
            console.log( error );
        }
    };

    return (
        <AppLayout>
            <h3 className="text-center">Lista de usuários</h3>
            <br />
            <Card className="p-3" style={{width: '25em'}}>
                <Card.Title>Campos do filtro</Card.Title>
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

                    <Button type="button" onClick={filter}>
                        Filtrar
                        <AppSpinner visible={loading} />
                    </Button>
                </Form>
            </Card>
            <br />
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>E-Mail</th>
                        <th>Username</th>
                    </tr>
                </thead>
                <tbody>
                    { usuarios.map( (usuario, index) => 
                        <tr key={index}>
                            <td>{usuario.id}</td>
                            <td>{usuario.nome}</td>
                            <td>{usuario.email}</td>
                            <td>{usuario.username}</td>
                        </tr> 
                    )}
                </tbody>
            </Table>
        </AppLayout>
    );
}

export default FilterUsuarios;