package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.role.*;
import com.redemonitor.main.dto.request.SaveRoleRequest;
import com.redemonitor.main.dto.response.RoleResponse;
import com.redemonitor.main.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @CreateRoleDoc
    @PreAuthorize("hasAuthority('role-all')")
    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody SaveRoleRequest request ) {
        roleService.createRole( request );
        return ResponseEntity.ok( "Role registrado com sucesso." );
    }

    @UpdateRoleDoc
    @PreAuthorize("hasAuthority('role-all')")
    @PutMapping("/{roleId}")
    public ResponseEntity<String> updateRole( @PathVariable Long roleId, @RequestBody SaveRoleRequest request ) {
        roleService.updateRole( roleId, request );
        return ResponseEntity.ok( "Role alterado com sucesso." );
    }

    @FilterRolesDoc
    @PreAuthorize("hasAuthority('role-all')")
    @GetMapping
    public ResponseEntity<List<RoleResponse>> filterRoles( @RequestParam("nomepart") String nomePart ) {
        List<RoleResponse> responses = roleService.filterRoles( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetRoleDoc
    @PreAuthorize("hasAuthority('role-all')")
    @GetMapping("/{roleId}/get")
    public ResponseEntity<RoleResponse> getRole( @PathVariable Long roleId ) {
        RoleResponse resp = roleService.getRole( roleId );
        return ResponseEntity.ok( resp );
    }

    @DeleteRoleDoc
    @PreAuthorize("hasAuthority('role-all')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole( @PathVariable Long roleId ) {
        roleService.deleteRole( roleId );
        return ResponseEntity.ok( "Role deletado com sucesso." );
    }

}

