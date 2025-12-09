import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Home() {

    const navigate = useNavigate();

    useEffect( () => {
        navigate( '/login/-1')
    }, [] );

    return (
        <></>
    )
}

export default Home;