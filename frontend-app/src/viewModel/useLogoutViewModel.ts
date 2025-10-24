import { useContext } from "react";
import { AuthContext } from "../context/AuthProvider";

export function useLogoutViewModel() {

    const {setNome, setUsername, setAccessToken} = useContext(AuthContext);

    const logout = async () => {        
        setNome( '' );
        setUsername( '' );
        setAccessToken( '' );
    };

    return { logout };
}