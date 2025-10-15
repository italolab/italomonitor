import { Button, Card, Table } from "react-bootstrap";
import useVincularUsuarioGrupoViewModel from "../../viewModel/usuario/useVincularUsuarioGrupoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { LuDelete, LuX } from "react-icons/lu";
import AppLayout from "../../layout/AppLayout";
import { FaX } from "react-icons/fa6";

function VincularUsuarioGrupo() {

    const {
        loadUsuario,
        usuario,
        grupos,
        errorMessage,
        infoMessage,
        loading
    } = useVincularUsuarioGrupoViewModel();

    const { usuarioId } = useParams();

    useEffect( () => {
        onLoad();
    }, [] )

    const onLoad = async () => {
        try {
            const uid : number = parseInt( usuarioId! );
            await loadUsuario( uid );
        } catch ( error ) {
            console.error( error );
        }
    }

    return (
        <AppLayout>
            <div className="d-flex justify-content-center">
                <Card style={{width: '30em'}}>
                    <Card.Header>
                        <Card.Title className="m-0 text-center">
                            <h3 className="m-0">Vínculo de usuário e grupo</h3>
                        </Card.Title>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />
                        <div className="d-flex justify-content-center">
                            <AppSpinner visible={loading} />
                        </div>

                        <p className="text-center">
                            Grupos do usuário: &nbsp;
                            <span className="text-primary">
                                {usuario.username}
                            </span>
                        </p>

                        <p className="d-flex justify-content-center">
                            <Button type="button">
                                Vincular grupo
                            </Button>
                        </p>

                        <Table striped bordered hover>
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Operações</th>
                                </tr>
                            </thead>
                            <tbody>
                                {grupos.map( (grupo, index) => 
                                    <tr key={index}>
                                        <td>{grupo.nome}</td>
                                        <td>
                                            <Button type="button" variant="link" className="text-decoration-none text-danger p-0">
                                                <FaX />
                                                remover
                                            </Button>
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </Table>
                    </Card.Body>
                </Card>
            </div>
        </AppLayout>
    )
}

export default VincularUsuarioGrupo;