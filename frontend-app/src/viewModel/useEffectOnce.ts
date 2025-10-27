import { useEffect, useRef } from "react";

function useEffectOnce( setup: () => void ) {

    const effectCalled = useRef( false );

    useEffect( () => {
        if ( !effectCalled.current ) {
            setup();
            effectCalled.current = true;            
        }
    }, [] );
    
}

export default useEffectOnce;