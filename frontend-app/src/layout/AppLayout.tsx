import { type ReactNode, useState } from "react";
import { Button, Dropdown, Navbar } from "react-bootstrap";
import { FaArrowLeftLong } from "react-icons/fa6";
import { HiOutlineMenu } from "react-icons/hi";
import { LuFilter, LuLogOut, LuUsersRound } from "react-icons/lu";
import { Link } from "react-router-dom";

interface AppLayoutProps {
    children: ReactNode;
}

function AppLayout( {children} : AppLayoutProps ) {

    const [sidebarVisible, setSidebarVisible] = useState<boolean>( false );

    return (
        <>
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
                        <FaArrowLeftLong />
                    </button>
                </Dropdown.Header>
                <Dropdown.ItemText>
                    <LuUsersRound /> &nbsp; Usuários
                </Dropdown.ItemText>
                <div className="px-3">
                    <Dropdown.Item eventKey="2">
                        <Link to="/filter-usuarios" className="text-white fw-normal d-flex align-items-center">
                            <LuFilter /> &nbsp; Filtrar usuários
                        </Link>
                    </Dropdown.Item>
                </div>
                <Dropdown.Item eventKey="3">
                    <button type="button" className="btn p-0 m-0 text-white d-flex align-items-center">
                        <LuLogOut />
                        &nbsp;
                        Sair
                    </button>
                </Dropdown.Item>
            </Dropdown.Menu>
            <div className="p-3 vw-100">
                {children}
            </div>
        </>
    );

}

export default AppLayout;
