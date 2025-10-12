import { Spinner } from "react-bootstrap";

interface AppSpinnerProps {
    visible : boolean;
    showEsp? : boolean;
}

function AppSpinner( { visible, showEsp = true } : AppSpinnerProps ) {

    return (
        <>            
            { (visible === true && showEsp === true) && <span>&nbsp;</span> }
            { visible === true &&                                           
                <Spinner animation="border" role="status" size="sm">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>                            
            }
        </>
    )

}

export default AppSpinner;