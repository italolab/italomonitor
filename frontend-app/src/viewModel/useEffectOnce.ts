import { useEffect, useRef } from "react";

function useEffectOnce( setup: (() => void) | (() => () => void) ) {

    const effectCalled = useRef( false );

    useEffect( () => {
        if ( !effectCalled.current ) {
            const returnFunc = setup();
            effectCalled.current = true;            

            return returnFunc;
        }

        return () => {};
    }, [setup] );
    
}

export default useEffectOnce;