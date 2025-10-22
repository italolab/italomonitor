import { useState } from "react";
import { type RoleResponse } from "../../model/dto/response/RoleResponse";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { RoleModel } from "../../model/RoleModel";


function useDetalhesRoleViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [role, setRole] = useState<RoleResponse>( {
        id: 0,
        nome: '',
    } );

    const roleModel = new RoleModel();

    const loadRole = async ( roleId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( false );

        try {
            const response = await roleModel.getRole( roleId );

            setRole( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    return { loadRole, role, loading, errorMessage, infoMessage };
}

export default useDetalhesRoleViewModel;