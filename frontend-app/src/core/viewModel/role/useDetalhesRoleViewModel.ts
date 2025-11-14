import { useContext, useState } from "react";
import { DEFAULT_ROLE_OBJ, type RoleResponse } from "../../model/dto/response/RoleResponse";
import { extractErrorMessage } from "../../util/sistema-util";
import { RoleModel } from "../../model/RoleModel";
import { AuthContext } from "../../../context/AuthProvider";


function useDetalhesRoleViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [role, setRole] = useState<RoleResponse>( DEFAULT_ROLE_OBJ );
    
    const {setAccessToken} = useContext(AuthContext);

    const roleModel = new RoleModel( setAccessToken );

    const loadRole = async ( roleId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

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