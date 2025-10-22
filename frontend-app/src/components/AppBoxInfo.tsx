import type { ReactNode } from "react";

interface AppBoxInfoProps {
    name: string;
    children: ReactNode;
}

function AppBoxInfo( {name, children} : AppBoxInfoProps ) {
    return (
        <div className="px-3 py-2 mx-2 bg-tertiary rounded-2">
            <div className="d-block text-white">
                <h6 className="m-0">{name}</h6>
            </div>
            <h3 className="d-flex justify-content-center text-dark mt-2">
                {children}
            </h3>
        </div>
    );
}

export default AppBoxInfo;