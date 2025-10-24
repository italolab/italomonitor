import { useContext } from "react";
import { AuthContext } from "../context/AuthProvider";
import { AuthModel } from "../model/AuthModel";

export function useLogoutViewModel() {

    const {setNome, setUsername, setAccessToken} = useContext(AuthContext);

    const authModel = new AuthModel( setAccessToken );

    const logout = async () => {        
        await authModel.logout();

        setNome( '' );
        setUsername( '' );
        setAccessToken( '' );
    };

    return { logout };
}