import { createContext, useState, type ReactNode } from "react";

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext( {
    nome: '',
    username: '',
    accessToken: '',
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setNome: (t : string) => {},
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setUsername: (t : string ) => {},
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setAccessToken: (t : string ) => {}
} );

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({children} : AuthProviderProps ) => {

    const [nome, setNome] = useState<string>('');
    const [username, setUsername] = useState<string>('');
    const [accessToken, setAccessToken] = useState<string>('');

    return (
        <AuthContext.Provider value={{nome, setNome, username, setUsername, accessToken, setAccessToken}}>
            {children}
        </AuthContext.Provider>
    );
};
