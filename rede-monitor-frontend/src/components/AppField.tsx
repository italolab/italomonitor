import type { ReactNode } from "react";

interface AppFieldProps {
    name: string;
    children: ReactNode;
}

function AppField( { name, children } : AppFieldProps ) {
    return (
        <div className="mb-3">
            <div className="d-block text-secondary">
                <h6 className="m-0">{name}</h6>
            </div>
            <div className="d-block text-dark">
                {children}
            </div>
        </div>
    );
}

export default AppField;