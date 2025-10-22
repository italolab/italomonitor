import { useContext } from "react";
import { AuthContext } from "../context/AuthProvider";

export function useLogoutViewModel() {

    const {setNome, setUsername} = useContext(AuthContext);

    const logout = async () => {
        setNome( '' );
        setUsername( '' );
    };

    return { logout };
}