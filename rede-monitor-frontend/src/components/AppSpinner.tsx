import { Spinner } from "react-bootstrap";

interface AppSpinnerProps {
    visible : boolean;
    showEsp? : boolean;
    className? : string;
}

function AppSpinner( { visible, className, showEsp = true } : AppSpinnerProps ) {

    return (
        <span className={className}>            
            { (visible === true && showEsp === true) && <span>&nbsp;</span> }
            { visible === true &&                                           
                <Spinner animation="border" role="status" size="sm">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>                            
            }
        </span>
    )

}

export default AppSpinner;