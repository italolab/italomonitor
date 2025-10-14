package com.redemonitor.service;

import com.redemonitor.dto.request.SaveRoleRequest;
import com.redemonitor.dto.response.RoleResponse;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.mapper.RoleMapper;
import com.redemonitor.model.Role;
import com.redemonitor.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public void createRole( SaveRoleRequest request ) {
        request.validate();

        Role role = roleMapper.map( request );
        String nome = role.getNome();

        Optional<Role> roleOp = roleRepository.findByNome( nome );
        if ( roleOp.isPresent() )
            throw new BusinessException( Errors.ROLE_ALREADY_EXISTS );

        roleRepository.save( role );
    }

    public void updateRole( Long id, SaveRoleRequest request ) {
        request.validate();

        String nome = request.getNome();

        Optional<Role> roleOp = roleRepository.findById( id );
        if ( roleOp.isEmpty() )
            throw new BusinessException( Errors.ROLE_NOT_FOUND );

        Role role = roleOp.get();
        if ( !role.getNome().equalsIgnoreCase( nome ) )
            if ( roleRepository.findByNome( nome ).isPresent() )
                throw new BusinessException( Errors.ROLE_ALREADY_EXISTS);

        roleMapper.load( role, request );

        roleRepository.save( role );
    }

    public List<RoleResponse> filterRoles( String nomePart ) {
        List<Role> roles = roleRepository.filter( "%"+nomePart+"%" );
        return roles.stream().map( roleMapper::map ).toList();
    }

    public RoleResponse getRole( Long id ) {
        Optional<Role> roleOp = roleRepository.findById( id );
        if ( roleOp.isEmpty() )
            throw new BusinessException( Errors.ROLE_NOT_FOUND );

        return roleOp.map( roleMapper::map ).orElseThrow();
    }

    public void deleteRole( Long id ) {
        Optional<Role> roleOp = roleRepository.findById( id );
        if ( roleOp.isEmpty() )
            throw new BusinessException( Errors.ROLE_NOT_FOUND );

        roleRepository.deleteById( id );
    }

}
