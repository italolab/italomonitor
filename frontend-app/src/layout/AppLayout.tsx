import { type ReactNode, useState } from "react";
import { Button, Container, Dropdown, Navbar } from "react-bootstrap";
import { FaArrowLeftLong } from "react-icons/fa6";
import { HiOutlineMenu } from "react-icons/hi";
import { LuFilter, LuLogOut, LuUsersRound } from "react-icons/lu";
import { Link, useNavigate } from "react-router-dom";
import { useLogoutViewModel } from "../viewModel/useLogoutViewModel";

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
        <div className={className}>
            <Navbar bg="dark" data-bs-theme="dark" className="vw-100 px-3">
                <Button type="button" variant="dark" onClick={ () => setSidebarVisible( !sidebarVisible ) }>              
                    <HiOutlineMenu color="white" fontSize={30}/>
                </Button>
                <Navbar.Brand href="#home" className="mx-3">Rede monitor</Navbar.Brand>
            </Navbar>
            <Dropdown.Menu show={sidebarVisible} variant="dark" className="vh-100" style={{width: '15em'}}>
                <Dropdown.Header className="d-flex align-items-center">
                    <span>Menu</span>
                    <button type="button" className="btn ms-auto" onClick={ () => setSidebarVisible( false ) }>
                        <FaArrowLeftLong color="white"/>
                    </button>
                </Dropdown.Header>
                <Dropdown.Item eventKey="1" onClick={ () => setUsuariosOptionsVisible( !usuariosOptionsVisible ) }>
                    <LuUsersRound /> &nbsp; Usuários
                </Dropdown.Item>
                <Container fluid hidden={!usuariosOptionsVisible} className="m-0 p-0">
                    <Dropdown.Item eventKey="2">
                        <Link to="/filter-usuarios" 
                                onClick={ () => setSidebarVisible( false ) } 
                                className="text-white fw-normal d-flex align-items-center px-3">
                            <LuFilter /> 
                            &nbsp; 
                            Filtrar usuários
                        </Link>
                    </Dropdown.Item>
                </Container>
                <Dropdown.Item eventKey="3" onClick={appLogout} className="text-white d-flex align-items-center">
                    <LuLogOut />
                    &nbsp;
                    Sair
                </Dropdown.Item>
            </Dropdown.Menu>
            <div className="p-3 vw-100">
                {children}
            </div>
        </div>
    );

}

export default AppLayout;
