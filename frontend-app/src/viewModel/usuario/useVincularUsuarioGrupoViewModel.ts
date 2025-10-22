import { useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import { extractErrorMessage } from "../../util/SistemaUtil";
import type { UsuarioGrupoResponse } from "../../model/dto/response/UsuarioGrupoResponse";
import type { UsuarioResponse } from "../../model/dto/response/UsuarioResponse";
import { UsuarioGrupoModel } from "../../model/UsuarioGrupoModel";

function useVincularUsuarioGrupoViewModel() {

    const [errorMessage, setErrorMessage] = useState<string|null>( null );
    const [infoMessage, setInfoMessage] = useState<string|null>( null );
    const [loading, setLoading] = useState<boolean>( false );

    const [usuario, setUsuario] = useState<UsuarioResponse>( {
        id: 0,
        nome: '',
        email: '',
        username: '',
        empresa: {
            id: 0,
            nome: '',
            emailNotif: ''
        },
        grupos: []
    } );

    const [otherGrupos, setOtherGrupos] = useState<UsuarioGrupoResponse[]>( [] );
    const [grupos, setGrupos] = useState<UsuarioGrupoResponse[]>( [] );

    const usuarioModel = new UsuarioModel();
    const usuarioGrupoModel = new UsuarioGrupoModel();

    const loadUsuario = async ( usuarioId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            const usuarioResponse = await usuarioModel.getUsuario( usuarioId );
            const gruposResponse = await usuarioModel.getGrupos( usuarioId );
            const allGruposResponse = await usuarioGrupoModel.filterUsuarioGrupos( "" );

            const allGrupos : UsuarioGrupoResponse[] = allGruposResponse.data;
            const grupos : UsuarioGrupoResponse[] = gruposResponse.data;
            const otherGrupos : UsuarioGrupoResponse[] = [];
            for( let i = 0; i < allGrupos.length; i++ ) {
                let found = false;
                for( let j = 0; found === false && j < grupos.length; j++ )
                    if ( allGrupos[ i ].id === grupos[ j ].id )
                        found = true;
                if ( found === false )
                    otherGrupos.push( allGrupos[ i ] );
            }

            setUsuario( usuarioResponse.data );
            setGrupos( grupos );
            setOtherGrupos( otherGrupos );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    };

    const vinculaGrupo = async ( usuarioGrupoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await usuarioModel.vinculaGrupo( usuario.id, usuarioGrupoId );

            let grp : UsuarioGrupoResponse | null = null;
            for( let i = 0; grp === null && i < otherGrupos.length; i++ ) {
                if ( otherGrupos[ i ].id == usuarioGrupoId ) {
                    grp = otherGrupos[ i ];
                    otherGrupos.splice( i, 1 );
                }
            }
            if ( grp !== null )
                grupos.push( grp );

            setInfoMessage( 'Vínculo bem sucedido.' );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    const removeGrupoVinculado = async ( usuarioGrupoId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );

        try {
            await usuarioModel.deleteGrupoVinculado( usuario.id, usuarioGrupoId );
            const response = await usuarioModel.getGrupos( usuario.id );

            let grp : UsuarioGrupoResponse | null = null;
            for( let i = 0; grp === null && i < grupos.length; i++ ) {
                if ( grupos[ i ].id == usuarioGrupoId ) {
                    grp = grupos[ i ];
                    grupos.splice( i, 1 );
                }
            }
            if ( grp !== null )
                otherGrupos.push( grp );

            setInfoMessage( 'Vínculo deletado com sucesso.' );
            setGrupos( response.data );
            setLoading( false );
        } catch ( error ) {
            setErrorMessage( extractErrorMessage( error ) );
            setLoading( false );
            throw error;
        }
    }

    return { 
        loadUsuario, 
        vinculaGrupo, 
        removeGrupoVinculado,
        usuario, 
        grupos, 
        otherGrupos, 
        loading, 
        errorMessage, 
        infoMessage 
    };
}

export default useVincularUsuarioGrupoViewModel;