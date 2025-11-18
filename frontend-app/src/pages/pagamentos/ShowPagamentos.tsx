import { useNavigate, useParams } from "react-router-dom";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useEffectOnce from "../../core/util/useEffectOnce";
import useShowPagamentosViewModel from "../../core/viewModel/pagamento/useShowPagamentosViewModel";
import AppLayout from "../../layout/AppLayout";
import { useState } from "react";
import type { EmpresaResponse } from "../../core/model/dto/response/EmpresaResponse";
import { formataDataMes, formataMoeda, stringToData, zonedData } from "../../core/util/sistema-util";
import { Button, Card, Form, Table } from "react-bootstrap";
import type { ConfigResponse } from "../../core/model/dto/response/ConfigResponse";
import useInfos from "../../core/viewModel/useInfos";

type Pagamento = {
    dataPagto : string;
    pago : boolean;
}

function ShowPagamentos() {

    const [pagamentos, setPagamentos] = useState<Pagamento[]>( [] );
    const [debito, setDebito] = useState<number>( 0 );

    const {
        getEmpresa,
        getNoAdminConfig,
        regularizaDivida,
        errorMessage,
        infoMessage,
        loading
    } = useShowPagamentosViewModel();

    const { isAdmin } = useInfos();

    const { empresaId } = useParams();

    const navigate = useNavigate();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            const empresa : EmpresaResponse = await getEmpresa( eid );
            const config : ConfigResponse = await getNoAdminConfig();

            const valorPagto = config.valorPagto;
            const usoRegularIniciadoEm = zonedData( empresa.usoRegularIniciadoEm );
            const pagoAte = zonedData( empresa.pagoAte );
            
            if ( empresa.usoRegularIniciadoEm !== null && empresa.pagoAte !== null ) {
                const dataAtual = new Date();

                let mes = usoRegularIniciadoEm.getMonth() + 1;
                let ano = usoRegularIniciadoEm.getFullYear();

                const mesPagoAte = pagoAte.getMonth() + 1;
                const anoPagoAte = pagoAte.getFullYear();

                let mesAtual = dataAtual.getMonth() + 1;
                const anoAtual = dataAtual.getFullYear();

                const diaPagto = empresa.diaPagto;
                const diaAtual = dataAtual.getDay();

                if ( diaAtual < diaPagto )
                    mesAtual--;

                const pagtos : Pagamento[] = [];
                let deb : number = 0;

                while( mes <= mesAtual || ano < anoAtual ) {
                    const dataPagto = stringToData( ''+ano+'-'+(mes < 10 ? '0'+mes : mes)+'-01 00:00:00' );                
                    let pago = true;
                    if ( mes > mesPagoAte && ano >= anoPagoAte ) {
                        pago = false;
                        deb += valorPagto;
                    }

                    pagtos.push( {
                        dataPagto: formataDataMes( dataPagto ),
                        pago: pago
                    } );

                    if ( mes == 12 ) {
                        mes = 1;
                        ano++;
                    } else {
                        mes++;
                    }
                }
                
                setDebito( deb );
                setPagamentos( pagtos );
            }
        } catch ( error ) {
            console.error( error );
        }
    };

    const onRegularizarDivida = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await regularizaDivida( eid );
            await onLoad();
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <h3 className="title">Pagamentos</h3>            

            <div className="d-flex flex-wrap justify-content-center mt-3">
                <Card>
                    <Card.Header>
                        Efetue o pagamento
                    </Card.Header>
                    <Card.Body className="p-3">
                        <h5>
                            Debito: 
                            <span className="text-danger">
                                { formataMoeda( debito ) }
                            </span>
                        </h5>

                        <Form>                            
                            <AppMessage message={errorMessage} type="error" />
                            <AppMessage message={infoMessage} type="info" />

                            { isAdmin() === true ?
                                <Button type="button" onClick={onRegularizarDivida}>
                                    Efetuar pagamento                        
                                    <AppSpinner visible={loading} />
                                </Button>
                            :
                                <Button type="button" onClick={() => navigate( `/efetuar-pagamento/${empresaId}` )}>
                                    Efetuar pagamento                        
                                    <AppSpinner visible={loading} />
                                </Button>
                            }
                        </Form>
                    </Card.Body>
                </Card>
            </div>

            <div className="w-100 overflow-auto mt-3">
                <Table striped hover>
                    <thead>
                        <tr className="blue">
                            <th>Data Pagto</th>
                            <th>Situação</th>
                        </tr>
                    </thead>
                    <tbody>
                        { pagamentos.map( (pagto, index) => 
                            <tr key={index}>
                                <td>{pagto.dataPagto}</td>
                                <td>
                                    { pagto.pago === true 
                                        ? <span className="text-primary fw-bold">Pago</span>
                                        : <span className="text-danger fw-bold">Em aberto</span>
                                    }
                                </td>                                
                            </tr> 
                        )}
                    </tbody>
                </Table>                               
            </div>

        </AppLayout>
    );    
}

export default ShowPagamentos;