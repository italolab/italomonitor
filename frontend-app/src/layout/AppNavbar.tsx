import { Container, Nav, Navbar } from "react-bootstrap";

function AppNavbar() {

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="#home">Rede monitor</Navbar.Brand>
                <Navbar.Toggle aria-controls="app-navbar" />
                <Navbar.Collapse id="app-navbar">
                    <Nav className="me-auto">
                        <Nav.Link href="#usuarios">Usuários</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );

}

export default AppNavbar;
