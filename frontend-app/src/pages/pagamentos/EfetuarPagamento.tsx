import { useNavigate, useParams } from "react-router-dom";
import AppMessage from "../../components/AppMessage";
import AppSpinner from "../../components/AppSpinner";
import useEfetuarPagamentoViewModel from "../../core/viewModel/pagamento/useEfetuarPagamentoViewModel";
import AppLayout from "../../layout/AppLayout";
import useEffectOnce from "../../core/util/useEffectOnce";
import { Button, Card, Image } from "react-bootstrap";
import { MdArrowBack } from "react-icons/md";

function EfetuarPagamento() {

    const {
        loadPixQrCode,
        copyToClipboard,
        pixQrCode,
        errorMessage,
        infoMessage,
        loading
    } = useEfetuarPagamentoViewModel();

    const { empresaId } = useParams();

    const navigate = useNavigate();

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

    
    const onCopiar = async () => {
        try {   
            await copyToClipboard();
        } catch ( error ) {
            console.error( error );
        }
    };

    return (
        <AppLayout>
            <div>
                <Button type="button" onClick={() => navigate( -1 )} className="d-inline-flex align-items-center">
                    <MdArrowBack size={25}/> Voltar
                </Button>                            
            </div>

            <h3 className="title">Efetuar pagamento</h3>
            
            <div className="d-flex flex-wrap justify-content-center">
                <Card>
                    <Card.Body>                        
                        <p><b>Por favor escaneie o qrcode abaixo no aplicativo do seu banco para 
                        efetuar o pagamento.</b></p>

                        { pixQrCode.image.length > 0 &&
                            <div className="d-flex">
                                <Image className="mx-auto bg-light-gray" src={pixQrCode.image} />
                            </div>
                        }                            

                        { pixQrCode.text.length > 0 &&
                            <div className="d-flex my-3">
                                    <>
                                        <Button type="button" className="mx-auto" onClick={onCopiar}>
                                            Copiar PIX
                                        </Button>
                                    </>
                            </div>
                        }

                        <AppMessage message={errorMessage} type="error" />
                        <AppMessage message={infoMessage} type="info" />

                        <div className="d-flex">
                            <AppSpinner className="mx-auto" visible={loading} />
                        </div>
                    </Card.Body>
                </Card> 
            </div>           
        </AppLayout>
    );
}

export default EfetuarPagamento;