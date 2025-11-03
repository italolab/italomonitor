import { Button, Card, Modal, Table } from "react-bootstrap";
import useVincularUsuarioGrupoViewModel from "../../core/viewModel/usuario/useVincularUsuarioGrupoViewModel";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import AppLayout from "../../layout/AppLayout";
import { FaLink, FaX } from "react-icons/fa6";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../core/util/useEffectOnce";

function VincularUsuarioGrupo() {

    const [vinculoModalVisible, setVinculoModalVisible] = useState<boolean>( false );

    const {
        loadUsuario,
        vinculaGrupo,
        removeGrupoVinculado,
        usuario,
        grupos,
        otherGrupos,
        errorMessage,
        infoMessage,
        loading
    } = useVincularUsuarioGrupoViewModel();

    const navigate = useNavigate();

    const { usuarioId } = useParams();

    useEffectOnce( () => {
        onLoad();
    } );

    const onVinculaGrupo = async ( usuarioGrupoId : number ) => {
        try {
            await vinculaGrupo( usuarioGrupoId );
            setVinculoModalVisible( false );
        } catch ( error ) {
            console.error( error );
        }
    }

    const onRemoveGrupoVinculado = async ( usuarioGrupoId : number ) => {
        try {
            await removeGrupoVinculado( usuarioGrupoId );
        } catch ( error ) {
            console.error( error );
        }
    }

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
            <Modal show={vinculoModalVisible} onHide={() => setVinculoModalVisible( false ) }>
                <Modal.Header closeButton>
                    <Modal.Title>
                        <h3 className="m-0">Vínculo de grupo</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Table striped bordered hover>
                        <thead>
                            <tr className="blue">
                                <td>Grupo de usuário</td>
                                <td>Operação</td>
                            </tr>
                        </thead>
                        <tbody>
                            { otherGrupos.map( (grupo, index) => 
                                <tr key={index}>
                                    <td>{grupo.nome}</td>
                                    <td>
                                        <Button type="button" variant="link" 
                                                className="text-decoration-none text-danger p-0" 
                                                onClick={() => onVinculaGrupo( grupo.id )}>
                                            <FaLink />
                                            vincular
                                        </Button>
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                </Modal.Body>
            </Modal>

            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25} /> Voltar
                </Button>
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card>
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
                            <Button type="button" onClick={() => setVinculoModalVisible( true )}>
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
                                            <Button type="button" variant="link" 
                                                    className="text-decoration-none text-danger p-0"
                                                    onClick={() => onRemoveGrupoVinculado( grupo.id )}>
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