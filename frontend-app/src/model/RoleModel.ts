import axios from "axios";
import type { SaveRoleRequest } from "./dto/request/SaveRoleRequest";
import { BASE_URL } from "../constants/api-constants";

export class RoleModel {

    async createRole( roleSave : SaveRoleRequest ) {
        return await axios.post( BASE_URL + "/roles", roleSave, {
            withCredentials: true
        } );
    }

    async updateRole( roleId : number, roleSave : SaveRoleRequest ) {
        return await axios.put( BASE_URL + "/roles/"+roleId, roleSave, {
            withCredentials: true
        } );
    } 

    async filterRoles( nomepart : string ) {
        return await axios.get( BASE_URL + "/roles?nomepart="+nomepart, {
            withCredentials: true
        } );
    }

    async getRole( roleId : number ) {
        return await axios.get( BASE_URL + "/roles/"+roleId+"/get", {
            withCredentials: true
        })
    }

    async deleteRole( roleId : number ) {
        return await axios.delete( BASE_URL + "/roles/"+roleId, {
            withCredentials: true
        } );
    }

}