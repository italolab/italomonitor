import { useContext, useState } from "react";
import { RoleModel } from "../../model/RoleModel";
import { extractErrorMessage } from "../../util/SistemaUtil";
import { AuthContext } from "../../context/AuthProvider";
import { type RoleResponse } from "../../model/dto/response/RoleResponse";

function useManterRoleViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [roles, setRoles] = useState<RoleResponse[]>( [] );

    const [nomePart, setNomePart] = useState<string>( '' );

    const {token} = useContext(AuthContext);

    const roleModel = new RoleModel();

    const filterRoles = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await roleModel.filterRoles( nomePart, token );

            setRoles( response.data );
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const removeRole = async ( roleId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await roleModel.deleteRole( roleId, token );
            const response = await roleModel.filterRoles( nomePart, token );

            setRoles( response.data );
            setInfoMessage( 'Grupo de usuário deletado com sucesso.' );            
            setLoading( false );
        } catch ( error ) {            
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getRoleById = ( roleId : number ) : RoleResponse | null => {
        for( let i = 0; i < roles.length; i++ )
            if ( roles[ i ].id === roleId )
                return roles[ i ];
        return null;
    };

    return { 
        filterRoles, 
        removeRole,         
        getRoleById,
        roles, 
        nomePart,        
        loading, 
        errorMessage,
        infoMessage,
        setNomePart
    };
}

export default useManterRoleViewModel;