import { type ReactNode, useState } from "react";
import { Button, Container, Navbar, Offcanvas } from "react-bootstrap";
import { FaArrowLeftLong } from "react-icons/fa6";
import { HiOutlineMenu } from "react-icons/hi";
import { Link, useNavigate } from "react-router-dom";
import { useLogoutViewModel } from "../core/viewModel/useLogoutViewModel";

import "./AppLayout.css"

interface AppLayoutProps {
    className?: string;
    children: ReactNode;
}

function AppLayout( {children, className} : AppLayoutProps ) {

    const [sidebarVisible, setSidebarVisible] = useState<boolean>( false );
    const [usuariosOptionsVisible, setUsuariosOptionsVisible] = useState<boolean>( false );

    const navigate = useNavigate();

    const { logout } = useLogoutViewModel();

    const appLogout = async () => {
        await logout();
        navigate( '/' );
    };

    return (
        <div className={`bg-tertiary h-100 ${className}`}>
            <Navbar bg="dark" data-bs-theme="dark" className="vw-100">
                <Button type="button" variant="dark" onClick={ () => setSidebarVisible( !sidebarVisible ) }>              
                    <HiOutlineMenu color="white" fontSize={30}/>
                </Button>
                <Navbar.Brand>Rede monitor</Navbar.Brand>
            </Navbar>
            <Offcanvas show={sidebarVisible} style={{width: '15em'}}>
                <Offcanvas.Header className="d-flex align-items-center bg-light">
                    <span>Menu</span>
                    <button type="button" className="btn ms-auto" onClick={ () => setSidebarVisible( false ) }>
                        <FaArrowLeftLong />
                    </button>
                </Offcanvas.Header>
                <Offcanvas.Body className="p-0 bg-light">                                        
                    <Link to="/dashboard" className="sidebar-item">
                        📈 Dashboard
                    </Link>

                    { localStorage.getItem( 'perfil' ) === 'USUARIO' &&
                        <span>
                            <Link to={`/dispositivos/${localStorage.getItem( 'empresaId' )}`} className="sidebar-item">
                                📥 Dispositivos
                            </Link>
                        </span>
                    }

                    { localStorage.getItem( 'perfil' ) === 'ADMIN' &&
                        <span>
                            <Link to="/empresas" className="sidebar-item">
                                🏢 Empresas
                            </Link>
                            <div className="sidebar-item" onClick={ () => setUsuariosOptionsVisible( !usuariosOptionsVisible ) }>
                                👤 Usuários
                            </div>
                            <Container fluid hidden={!usuariosOptionsVisible} className="m-0 p-0">
                                <Link to="/usuarios" className="sidebar-item px-5">
                                    👤 Usuarios
                                </Link>
                                <Link to="/usuario-grupos" className="sidebar-item px-5">
                                    👥 Grupos
                                </Link>
                                <Link to="/roles" className="sidebar-item px-5">
                                    📄 Roles
                                </Link>
                            </Container>

                            <Link to="/detalhes-config" className="sidebar-item">
                                ⚙️ Configurações
                            </Link>
                        </span>
                    }

                    <div onClick={appLogout} className="sidebar-item">
                        ↙️ Sair
                    </div>
                </Offcanvas.Body>
            </Offcanvas>
            <div className="vw-100 p-3">
                {children}
            </div>
        </div>
    );

}

export default AppLayout;
