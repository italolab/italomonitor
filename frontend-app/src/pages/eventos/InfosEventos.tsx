import { useState } from "react";
import { Nav } from "react-bootstrap";
import ListEventosByDia from "./ListEventosByDia";
import ListEventosByIntervalo from "./ListEventosByIntervalo";
import ShowEventosGraficos from "./ShowEventosGraficos";
import AppLayout from "../../layout/AppLayout";

import './style/InfosEventos.css'

function InfosEventos() {

    const LIST_EVENTOS_BY_DIA = 'list-eventos-by-dia';
    const LIST_EVENTOS_BY_INTERVALO = "list-eventos-by-intervalo";
    const SHOW_EVENTOS_GRAFICOS = "show-eventos-grafico";

    const [visiblePage, setVisiblePage] = useState<string>( LIST_EVENTOS_BY_DIA );

    return (
        <AppLayout>
            <h3 className="title">Informações de eventos</h3>

            <Nav variant="tabs" defaultActiveKey={visiblePage} fill={true}>
                <Nav.Item>
                    <Nav.Link className="tabbed-item-link" eventKey={LIST_EVENTOS_BY_DIA} onClick={() => setVisiblePage( LIST_EVENTOS_BY_DIA )}>
                        Eventos por dia
                    </Nav.Link>                    
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link className="tabbed-item-link" eventKey={LIST_EVENTOS_BY_INTERVALO} onClick={() => setVisiblePage( LIST_EVENTOS_BY_INTERVALO )}>
                        Eventos por intervalo
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link className="tabbed-item-link" eventKey={SHOW_EVENTOS_GRAFICOS} onClick={() => setVisiblePage( SHOW_EVENTOS_GRAFICOS )}>
                        Eventos gráfico
                    </Nav.Link>
                </Nav.Item>
            </Nav>

            <br />

            { visiblePage === LIST_EVENTOS_BY_DIA && <ListEventosByDia />}
            { visiblePage === LIST_EVENTOS_BY_INTERVALO && <ListEventosByIntervalo />}
            { visiblePage === SHOW_EVENTOS_GRAFICOS && <ShowEventosGraficos />}
        </AppLayout>
    )
}

export default InfosEventos;