import { createContext, useEffect, useState, type ReactNode } from "react";

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext( {
    token: '',
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    setToken: (t : string) => {}
} );

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({children} : AuthProviderProps ) => {

    const [token, setToken] = useState<string>( () => {
        if ( typeof window !== 'undefined' )
            return localStorage.getItem( 'token' )!;                            
        return '';
    } );

    useEffect( () => {
        localStorage.setItem( 'token', token! );
    }, [token] );

    return (
        <AuthContext.Provider value={{token, setToken}}>
            {children}
        </AuthContext.Provider>
    );
};
