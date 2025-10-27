import { useContext, useState } from "react";
import { RoleModel } from "../../model/RoleModel";
import { extractErrorMessage } from "../../util/sistema-util";
import { type RoleResponse } from "../../model/dto/response/RoleResponse";
import { AuthContext } from "../../context/AuthProvider";

function useManterRoleViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [roles, setRoles] = useState<RoleResponse[]>( [] );

    const [nomePart, setNomePart] = useState<string>( '' );
    
    const {setAccessToken} = useContext(AuthContext);

    const roleModel = new RoleModel( setAccessToken );

    const filterRoles = async () => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const response = await roleModel.filterRoles( nomePart );

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
            await roleModel.deleteRole( roleId );
            const response = await roleModel.filterRoles( nomePart );

            setRoles( response.data );
            setInfoMessage( 'Role deletado com sucesso.' );            
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