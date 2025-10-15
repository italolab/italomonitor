import axios from "axios";
import type { SaveRoleRequest } from "./dto/request/SaveRoleRequest";
import { BASE_URL } from "../constants/api-constants";

export class RoleModel {

    async createRole( roleSave : SaveRoleRequest, token : string ) {
        return await axios.post( BASE_URL + "/roles", roleSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    }

    async updateRole( roleId : number, roleSave : SaveRoleRequest, token : string ) {
        return await axios.put( BASE_URL + "/roles/"+roleId, roleSave, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        } );
    } 

    async filterRoles( nomepart : string, token : string ) {
        return await axios.get( BASE_URL + "/roles?nomepart="+nomepart, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

    async getRole( roleId : number, token : string ) {
        return await axios.get( BASE_URL + "/roles/"+roleId+"/get", {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        })
    }

    async deleteRole( roleId : number, token : string ) {
        return await axios.delete( BASE_URL + "/roles/"+roleId, {
            headers: {
                'Authorization' : `Bearer ${token}`
            }
        } );
    }

}