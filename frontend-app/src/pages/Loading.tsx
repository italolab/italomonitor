import { Image } from "react-bootstrap";

function Loading() {
    return (
        <div className="d-flex justify-content-center align-items-center vw-100 vh-100 bg-tertiary">
            <Image src="/redes.png" rounded />
        </div>
    );
}

export default Loading;