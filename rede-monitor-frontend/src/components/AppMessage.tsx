import { Alert } from "react-bootstrap";

interface MessageProps {
    message: string | null | undefined;
    type: 'error' | 'info';
}

function AppMessage( { message, type } : MessageProps ) {
    return (
        <>
            { message === null || message === undefined ? 
                <></>
            :
                <>
                    { type === "error" ?
                        <Alert key="danger" variant="danger">{message}</Alert>
                    :
                        <Alert key="success" variant="success">{message}</Alert>
                    }
                </>
            }
        </>
    );
}

export default AppMessage;