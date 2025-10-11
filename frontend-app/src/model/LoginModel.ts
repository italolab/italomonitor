import axios from "axios";
import { BASE_URL } from "../constants/api-constants";
import type { LoginRequest } from "./dto/request/LoginRequest";

export class LoginModel {

    async login( loginObj : LoginRequest ) {
        return await axios.post( BASE_URL + "/login", loginObj );
    }

}