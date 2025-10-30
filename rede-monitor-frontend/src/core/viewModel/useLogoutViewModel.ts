import { useContext } from "react";
import { AuthContext } from "../../context/AuthProvider";

export function useLogoutViewModel() {

    const {setNome, setUsername, setAccessToken} = useContext(AuthContext);

    const logout = async () => {      
        /*const response = await authModel.logout();  
        if ( response.status !== 200 )
            alert( 'Sistema indisponível.' );*/

        setNome( '' );
        setUsername( '' );
        setAccessToken( '' );
    };

    return { logout };
}