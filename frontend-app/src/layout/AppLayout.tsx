import { type ReactNode, useState } from "react";
import { Button, Container, Navbar, Offcanvas } from "react-bootstrap";
import { FaArrowLeftLong, FaRegBuilding } from "react-icons/fa6";
import { HiOutlineMenu } from "react-icons/hi";
import { LuGroup, LuLogOut, LuUsers, LuUsersRound } from "react-icons/lu";
import { Link, useNavigate } from "react-router-dom";
import { useLogoutViewModel } from "../viewModel/useLogoutViewModel";
import { PiDevices, PiNote } from "react-icons/pi";

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
                    
                <Link to="/empresas" 
                        //onClick={ () => setSidebarVisible( false ) } 
                        className="sidebar-item">
                    <FaRegBuilding /> 
                    &nbsp; 
                    Empresas
                </Link>

                <Link to="/dispositivos" 
                        // onClick={ () => setSidebarVisible( false ) } 
                        className="sidebar-item">
                    <PiDevices /> 
                    &nbsp; 
                    Dispositivos
                </Link>

                <div className="sidebar-item" onClick={ () => setUsuariosOptionsVisible( !usuariosOptionsVisible ) }>
                    <LuUsersRound /> &nbsp; Usuários
                </div>
                <Container fluid hidden={!usuariosOptionsVisible} className="m-0 p-0">
                    <Link to="/usuarios" 
                            onClick={ () => setSidebarVisible( false ) } 
                            className="sidebar-item px-5">
                        <LuUsers /> 
                        &nbsp; 
                        Usuarios
                    </Link>
                    <Link to="/usuario-grupos" 
                            onClick={ () => setSidebarVisible( false ) } 
                            className="sidebar-item px-5">
                        <LuGroup /> 
                        &nbsp; 
                        Grupos
                    </Link>
                    <Link to="/roles" 
                            onClick={ () => setSidebarVisible( false ) } 
                            className="sidebar-item px-5">
                        <PiNote /> 
                        &nbsp; 
                        Roles
                    </Link>
                </Container>
                <div onClick={appLogout} className="sidebar-item">
                    <LuLogOut />
                    &nbsp;
                    Sair
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
