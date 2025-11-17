import { useParams } from "react-router-dom";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useEfetuarPagamentoViewModel from "../../core/viewModel/pagamento/useEfetuarPagamentoViewModel";
import AppLayout from "../../layout/AppLayout";
import useEffectOnce from "../../core/util/useEffectOnce";
import { Card, Image } from "react-bootstrap";

function EfetuarPagamento() {

    const {
        loadPixQrCode,
        pixQrCode,
        errorMessage,
        infoMessage,
        loading
    } = useEfetuarPagamentoViewModel();

    const { empresaId } = useParams();

    useEffectOnce( () => {
        onLoad();
    } );

    const onLoad = async () => {
        try {
            const eid : number = parseInt( empresaId! );
            await loadPixQrCode( eid );
        } catch ( error ) { 
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <h3 className="title">Efetuar pagamento</h3>

            <AppMessage message={errorMessage} type="error" />
            <AppMessage message={infoMessage} type="info" />

            <div className="d-flex">
                <AppSpinner className="mx-auto" visible={loading} />
            </div>

            <div className="d-flex flex-wrap justify-content-center">
                <Card>
                    <Card.Body>
                        <p>Por favor escaneie o qrcode abaixo no aplicativo do seu banco para 
                        efetuar o pagamento.</p>

                        <div className="d-flex justify-content-center">
                            { pixQrCode.image.length > 0 &&
                                <Image className="bg-light-gray" src={pixQrCode.image} />
                            }
                        </div>
                    </Card.Body>
                </Card> 
            </div>           
        </AppLayout>
    );
}

export default EfetuarPagamento;