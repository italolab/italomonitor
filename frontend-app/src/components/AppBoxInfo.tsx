import type { ReactNode } from "react";

interface AppBoxInfoProps {
    name: string;
    children: ReactNode;
}

function AppBoxInfo( {name, children} : AppBoxInfoProps ) {
    return (
        <div className="px-3 py-2 mx-2 bg-complementar rounded-2">
            <div className="d-block text-dark">
                <h6 className="m-0">{name}</h6>
            </div>
            <h3 className="d-flex justify-content-center text-dark mt-2">
                <span>{children}</span>
            </h3>
        </div>
    );
}

export default AppBoxInfo;