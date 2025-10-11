import { Spinner } from "react-bootstrap";

interface AppSpinnerProps {
    visible : boolean;
}

function AppSpinner( { visible } : AppSpinnerProps ) {

    return (
        <>
            { visible === true &&            
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>                            
            }
        </>
    )

}

export default AppSpinner;