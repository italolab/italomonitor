import axios from "axios";
import { BASE_URL } from "../constants/api-constants";


export class UsuarioModel {

    async filterUsuarios( nomepart : string, token : string ) {
        return await axios.get( BASE_URL + "/usuarios?nomepart="+nomepart, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

}