import { Spinner } from "react-bootstrap";

interface AppSpinnerProps {
    visible : boolean;
    showEsp? : boolean;
    className? : string;
    variant?: string;
}

function AppSpinner( { visible, className, variant, showEsp = true } : AppSpinnerProps ) {

    return (
        <span className={className}>            
            { (visible === true && showEsp === true) && <span>&nbsp;</span> }
            { visible === true &&                                           
                <Spinner animation="border" role="status" size="sm" variant={variant}>
                    <span className="visually-hidden">Loading...</span>
                </Spinner>                            
            }
        </span>
    )

}

export default AppSpinner;