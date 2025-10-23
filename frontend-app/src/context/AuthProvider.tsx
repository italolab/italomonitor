import { createContext, useState, type ReactNode } from "react";

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext( {
    nome: '',
    username: '',
    token: '',
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setNome: (t : string) => {},
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setUsername: (t : string ) => {},
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setToken: (t : string ) => {}
} );

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({children} : AuthProviderProps ) => {

    const [nome, setNome] = useState<string>('');
    const [username, setUsername] = useState<string>('');
    const [token, setToken] = useState<string>('');

    return (
        <AuthContext.Provider value={{nome, setNome, username, setUsername, token, setToken}}>
            {children}
        </AuthContext.Provider>
    );
};
