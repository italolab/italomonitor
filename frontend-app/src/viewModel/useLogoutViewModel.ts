import { useContext } from "react";
import { AuthContext } from "../context/AuthProvider";

export function useLogoutViewModel() {

    const {setToken} = useContext(AuthContext);

    const logout = async () => {
        setToken( '' );
    };

    return { logout };
}