import { createContext, useState, type ReactNode } from "react";

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext( {    
    accessToken: '',    
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setAccessToken: (t : string ) => {}
} );

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({children} : AuthProviderProps ) => {
    
    const [accessToken, setAccessToken] = useState<string>('');

    return (
        <AuthContext.Provider value={{accessToken, setAccessToken}}>
            {children}
        </AuthContext.Provider>
    );
};
