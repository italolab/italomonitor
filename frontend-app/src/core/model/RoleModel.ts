import { api, configuraInterceptor, type SetAccessTokenFunction } from "./Api";
import type { SaveRoleRequest } from "../model/dto/request/SaveRoleRequest";

export class RoleModel {

    constructor( setAccessToken : SetAccessTokenFunction ) {
        configuraInterceptor( setAccessToken );
    }

    async createRole( roleSave : SaveRoleRequest ) {
        return await api.post( "/roles", roleSave );
    }

    async updateRole( roleId : number, roleSave : SaveRoleRequest ) {
        return await api.put( "/roles/"+roleId, roleSave );
    } 

    async filterRoles( nomepart : string ) {
        return await api.get( "/roles?nomepart="+nomepart );
    }

    async getRole( roleId : number ) {
        return await api.get( "/roles/"+roleId+"/get" );
    }

    async deleteRole( roleId : number ) {
        return await api.delete( "/roles/"+roleId );
    }

}