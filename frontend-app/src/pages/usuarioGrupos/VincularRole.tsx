import { Button, Card, Modal, Table } from "react-bootstrap";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import AppLayout from "../../layout/AppLayout";
import { FaLink, FaX } from "react-icons/fa6";
import useVincularRoleViewModel from "../../viewModel/usuarioGrupo/useVincularRoleViewModel";
import { MdArrowBack } from "react-icons/md";
import useEffectOnce from "../../util/useEffectOnce";

function VincularRole() {

    const [vinculoModalVisible, setVinculoModalVisible] = useState<boolean>( false );

    const {
        loadUsuarioGrupo,
        vinculaRole,
        removeRoleVinculado,
        usuarioGrupo,
        roles,
        otherRoles,
        errorMessage,
        infoMessage,
        loading
    } = useVincularRoleViewModel();

    const { usuarioGrupoId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } );

    const onVinculaRole = async ( roleId : number ) => {
        try {
            await vinculaRole( roleId );
            setVinculoModalVisible( false );
        } catch ( error ) {
            console.error( error );
        }
    }

    const onRemoveRoleVinculado = async ( roleId : number ) => {
        try {
            await removeRoleVinculado( roleId );
        } catch ( error ) {
            console.error( error );
        }
    }

    const onLoad = async () => {
        try {
            const gid : number = parseInt( usuarioGrupoId! );
            await loadUsuarioGrupo( gid );
        } catch ( error ) {
            console.error( error );
        }
    }

    return (
        <AppLayout>
            <Modal show={vinculoModalVisible} onHide={() => setVinculoModalVisible( false ) }>
                <Modal.Header closeButton>
                    <Modal.Title>
                        <h3 className="m-0">Vínculo de role</h3>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Table striped bordered hover>
                        <thead>
                            <tr className="blue">
                                <td>Role</td>
                                <td>Operação</td>
                            </tr>
                        </thead>
                        <tbody>
                            { otherRoles.map( (role, index) => 
                                <tr key={index}>
                                    <td>{role.nome}</td>
                                    <td>
                                        <Button type="button" variant="link" 
                                                className="text-decoration-none text-danger p-0" 
                                                onClick={() => onVinculaRole( role.id )}>
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

            <div className="d-flex justify-content-start">
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <div className="d-flex justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        <Card.Title className="m-0 text-center">
                            <h3 className="m-0">Vínculo de role</h3>
                        </Card.Title>
                    </Card.Header>
                    <Card.Body>
                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />
                        <div className="d-flex justify-content-center">
                            <AppSpinner visible={loading} />
                        </div>

                        <p className="text-center">
                            Role do grupo: &nbsp;
                            <span className="text-primary">
                                {usuarioGrupo.nome}
                            </span>
                        </p>

                        <p className="d-flex justify-content-center">
                            <Button type="button" onClick={() => setVinculoModalVisible( true )}>
                                Vincular role
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
                                {roles.map( (role, index) => 
                                    <tr key={index}>
                                        <td>{role.nome}</td>
                                        <td>
                                            <Button type="button" variant="link" 
                                                    className="text-decoration-none text-danger p-0"
                                                    onClick={() => onRemoveRoleVinculado( role.id )}>
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

export default VincularRole;