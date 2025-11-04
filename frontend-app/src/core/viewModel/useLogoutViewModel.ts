import { useContext } from "react";
import { AuthContext } from "../../context/AuthProvider";

export function useLogoutViewModel() {

    const {setAccessToken} = useContext(AuthContext);

    const logout = async () => {      
        /*const response = await authModel.logout();  
        if ( response.status !== 200 )
            alert( 'Sistema indisponível.' );*/
        
        setAccessToken( '' );
        localStorage.removeItem( 'username' );
        localStorage.removeItem( 'nome' );
        localStorage.removeItem( 'empresaId' );
        localStorage.removeItem( 'isAdmin' );
    };

    return { logout };
}