import { FaRegEdit } from "react-icons/fa";
import { LuInfo } from "react-icons/lu";
import { RiDeleteBin6Line } from "react-icons/ri";
import { Link } from "react-router-dom";

interface AppOperationsProps {
    toDetalhes : string;
    toEdit : string;
    onRemover() : void;
}

function AppOperations( { toDetalhes, toEdit, onRemover } : AppOperationsProps ) {
    return (
        <div className="d-flex align-items-center">
            <Link to={toDetalhes} className="mx-2">
                <LuInfo color="green" size={20}/>
            </Link>
            |
            <Link to={toEdit} className="mx-2">
                <FaRegEdit size={20} />
            </Link>
            |
            <button type="button" onClick={onRemover} className="btn m-0 p-0 mx-1">
                <RiDeleteBin6Line color="red" size={20} />
            </button>
        </div>
    )
}

export default AppOperations;