import { useNavigate, useParams } from "react-router-dom";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useEffectOnce from "../../core/util/useEffectOnce";
import useShowPagamentosViewModel from "../../core/viewModel/pagamento/useShowPagamentosViewModel";
import AppLayout from "../../layout/AppLayout";
import { formataDataMes, formataMoeda } from "../../core/util/sistema-util";
import { Button, Card, Form, Table } from "react-bootstrap";
import useInfos from "../../core/viewModel/useInfos";
import { useState } from "react";
import type { PagamentoResponse } from "../../core/model/dto/response/PagamentoResponse";
import AppPagination from "../../components/AppPagination";

function ShowPagamentos() {

    const [paginationPagamentos, setPaginationPagamentos] = useState<PagamentoResponse[]>( [] );

    const {
        load,
        regularizaDivida,
        pagamentosDados,
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
            await load( eid );            
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
                                { formataMoeda( pagamentosDados.valorDebito ) }
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
                        { paginationPagamentos.map( (pagto, index) => 
                            <tr key={index}>
                                <td>{formataDataMes( pagto.dataPagto )}</td>
                                <td>
                                    { pagto.paga === true 
                                        ? <span className="text-primary fw-bold">Pago</span>
                                        : <span className="text-danger fw-bold">Em aberto</span>
                                    }
                                </td>                                
                            </tr> 
                        )}
                    </tbody>
                </Table>                               

                <div className="d-flex justify-content-center">
                    <AppPagination 
                        dataList={pagamentosDados.pagamentos}
                        numberOfItemsByPage={10}
                        numberOfPagesByGroup={3}
                        onChangePageDataList={ (pageDataList : PagamentoResponse[]) => setPaginationPagamentos( pageDataList )}
                    />
                </div>
            </div>

        </AppLayout>
    );    
}

export default ShowPagamentos;