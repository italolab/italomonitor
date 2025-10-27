import { useContext, useState } from "react";
import { RoleModel } from "../../model/RoleModel";
import { extractErrorMessage } from "../../util/sistema-util";
import type { RoleResponse } from "../../model/dto/response/RoleResponse";
import type { SaveRoleRequest } from "../../model/dto/request/SaveRoleRequest";
import { AuthContext } from "../../context/AuthProvider";


function useSaveRoleViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );
    
    const {setAccessToken} = useContext(AuthContext);

    const roleModel = new RoleModel( setAccessToken );

    const createRole = async ( role : SaveRoleRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            await roleModel.createRole( role );

            setInfoMessage( 'Role registrado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const updateRole = async ( roleId : number, role : SaveRoleRequest ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            await roleModel.updateRole( roleId, role );

            setInfoMessage( 'Role alterado com sucesso.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const getRole = async ( roleId : number ) : Promise<RoleResponse> => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        
        try {
            const response = await roleModel.getRole( roleId );
            
            setLoading( false );
            return response.data;
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        createRole, 
        updateRole, 
        getRole, 
        loading, 
        errorMessage, 
        infoMessage, 
        setErrorMessage };    
}

export default useSaveRoleViewModel;