import { useState } from "react";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";
import { UsuarioGrupoModel } from "../../model/UsuarioGrupoModel";
import { RoleModel } from "../../model/RoleModel";
import type { RoleResponse } from "../../model/dto/response/RoleResponse";

function useVincularRoleViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuarioGrupo, setUsuarioGrupo] = useState<UsuarioGrupoResponse>( {
        id: 0,
        nome: ''
    } );

    const [otherRoles, setOtherRoles] = useState<UsuarioGrupoResponse[]>( [] );
    const [roles, setRoles] = useState<UsuarioGrupoResponse[]>( [] );

    const usuarioGrupoModel = new UsuarioGrupoModel();
    const roleModel = new RoleModel();

    const loadUsuarioGrupo = async ( usuarioGrupoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const grupoResponse = await usuarioGrupoModel.getUsuarioGrupo( usuarioGrupoId );
            const rolesResponse = await usuarioGrupoModel.getRoles( usuarioGrupoId );
            const allRolesResponse = await roleModel.filterRoles( "" );

            const allRoles : RoleResponse[] = allRolesResponse.data;
            const roles : RoleResponse[] = rolesResponse.data;
            const otherRoles : RoleResponse[] = [];
            for( let i = 0; i < allRoles.length; i++ ) {
                let found = false;
                for( let j = 0; found === false && j < roles.length; j++ )
                    if ( allRoles[ i ].id === roles[ j ].id )
                        found = true;
                if ( found === false )
                    otherRoles.push( allRoles[ i ] );
            }

            setUsuarioGrupo( grupoResponse.data );
            setRoles( roles );
            setOtherRoles( otherRoles );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const vinculaRole = async ( roleId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await usuarioGrupoModel.vinculaRole( usuarioGrupo.id, roleId );

            let role : RoleResponse | null = null;
            for( let i = 0; role === null && i < otherRoles.length; i++ ) {
                if ( otherRoles[ i ].id == roleId ) {
                    role = otherRoles[ i ];
                    otherRoles.splice( i, 1 );
                }
            }
            if ( role !== null )
                roles.push( role );

            setInfoMessage( 'Vínculo bem sucedido.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const removeRoleVinculado = async ( roleId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await usuarioGrupoModel.deleteRoleVinculado( usuarioGrupo.id, roleId );
            const response = await usuarioGrupoModel.getRoles( usuarioGrupo.id );

            let role : RoleResponse | null = null;
            for( let i = 0; role === null && i < roles.length; i++ ) {
                if ( roles[ i ].id == roleId ) {
                    role = roles[ i ];
                    roles.splice( i, 1 );
                }
            }
            if ( role !== null )
                otherRoles.push( role );

            setInfoMessage( 'Vínculo deletado com sucesso.' );
            setRoles( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        loadUsuarioGrupo, 
        vinculaRole, 
        removeRoleVinculado,
        usuarioGrupo, 
        roles, 
        otherRoles, 
        loading, 
        errorMessage, 
        infoMessage 
    };
}

export default useVincularRoleViewModel;