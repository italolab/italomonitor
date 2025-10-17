import { useContext, useState } from "react";
import { UsuarioModel } from "../../model/UsuarioModel";
import { AuthContext } from "../../context/AuthProvider";
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
        username: ''
    } );

    const [otherGrupos, setOtherGrupos] = useState<UsuarioGrupoResponse[]>( [] );
    const [grupos, setGrupos] = useState<UsuarioGrupoResponse[]>( [] );

    const usuarioModel = new UsuarioModel();
    const usuarioGrupoModel = new UsuarioGrupoModel();

    const {token} = useContext(AuthContext);

    const loadUsuario = async ( usuarioId : number ) => {
        setErrorMessage( null );
        setInfoMessage( null );
        setLoading( true );
        try {
            const usuarioResponse = await usuarioModel.getUsuario( usuarioId, token );
            const gruposResponse = await usuarioModel.getGrupos( usuarioId, token );
            const allGruposResponse = await usuarioGrupoModel.filterUsuarioGrupos( "", token );

            const allGrupos : [] = allGruposResponse.data;
            const grupos : [] = gruposResponse.data;
            const otherGrupos : [] = [];
            for( let i = 0; i < allGrupos.length; i++ ) {
                let found = false;
                for( let j = 0; found === false && j < grupos.length; i++ )
                    if ( allGrupos[ i ] == grupos[ j ] )
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

    const vincularGrupo = async ( usuarioGrupoId : number ) => {
        let grp : UsuarioGrupoResponse | null = null;
        for( let i = 0; grp === null && i < otherGrupos.length; i++ ) {
            if ( otherGrupos[ i ].id == usuarioGrupoId ) {
                grp = otherGrupos[ i ];
                otherGrupos.splice( i, 1 );
            }
        }
        if ( grp !== null )
            grupos.push( grp );
    }

    return { loadUsuario, usuario, grupos, otherGrupos, loading, errorMessage, infoMessage };
}

export default useVincularUsuarioGrupoViewModel;