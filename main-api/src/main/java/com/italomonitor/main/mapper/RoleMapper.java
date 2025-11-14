package com.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.italomonitor.main.dto.request.SaveRoleRequest;
import com.italomonitor.main.dto.response.RoleResponse;
import com.italomonitor.main.model.Role;

@Component
public class RoleMapper {

    public Role map(SaveRoleRequest request ) {
        return Role.builder()
                .nome( request.getNome() )
                .build();
    }

    public RoleResponse map( Role role ) {
        return RoleResponse.builder()
                .id( role.getId() )
                .nome( role.getNome() )
                .build();
    }

    public void load(Role usuario, SaveRoleRequest request ) {
        usuario.setNome( request.getNome() );
    }

}
