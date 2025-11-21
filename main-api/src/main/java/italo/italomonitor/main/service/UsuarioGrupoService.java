package italo.italomonitor.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import italo.italomonitor.main.dto.request.SaveUsuarioGrupoRequest;
import italo.italomonitor.main.dto.response.RoleResponse;
import italo.italomonitor.main.dto.response.UsuarioGrupoResponse;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.mapper.RoleMapper;
import italo.italomonitor.main.mapper.UsuarioGrupoMapper;
import italo.italomonitor.main.model.Role;
import italo.italomonitor.main.model.RoleGrupoMap;
import italo.italomonitor.main.model.UsuarioGrupo;
import italo.italomonitor.main.repository.RoleGrupoMapRepository;
import italo.italomonitor.main.repository.RoleRepository;
import italo.italomonitor.main.repository.UsuarioGrupoRepository;

@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleGrupoMapRepository roleGrupoMapRepository;

    @Autowired
    private UsuarioGrupoMapper usuarioGrupoMapper;

    @Autowired
    private RoleMapper roleMapper;

    public void createUsuarioGrupo( SaveUsuarioGrupoRequest request ) {
        request.validate();

        UsuarioGrupo usuarioGrupo = usuarioGrupoMapper.map( request );
        String nome = usuarioGrupo.getNome();

        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findByNome( nome );
        if ( usuarioGrupoOp.isPresent() )
            throw new BusinessException( Errors.USER_GROUP_ALREADY_EXISTS );

        usuarioGrupoRepository.save( usuarioGrupo );
    }

    public void updateUsuarioGrupo( Long grupoId, SaveUsuarioGrupoRequest request ) {
        request.validate();

        String nome = request.getNome();

        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findById( grupoId );
        if ( usuarioGrupoOp.isEmpty() )
            throw new BusinessException( Errors.USER_GROUP_NOT_FOUND );

        UsuarioGrupo usuarioGrupo = usuarioGrupoOp.get();
        if ( !usuarioGrupo.getNome().equalsIgnoreCase( nome ) )
            if ( usuarioGrupoRepository.findByNome( nome ).isPresent() )
                throw new BusinessException(Errors.USER_GROUP_ALREADY_EXISTS);

        usuarioGrupoMapper.load( usuarioGrupo, request );

        usuarioGrupoRepository.save( usuarioGrupo );
    }

    public List<UsuarioGrupoResponse> filterUsuarioGrupos(String nomePart ) {
        List<UsuarioGrupo> usuarioGrupos = usuarioGrupoRepository.filter( "%"+nomePart+"%" );
        return usuarioGrupos.stream().map( usuarioGrupoMapper::map ).toList();
    }

    public UsuarioGrupoResponse getUsuarioGrupo( Long grupoId ) {
        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findById( grupoId );
        if ( usuarioGrupoOp.isEmpty() )
            throw new BusinessException( Errors.USER_GROUP_NOT_FOUND );

        return usuarioGrupoOp.map( usuarioGrupoMapper::map ).orElseThrow();
    }

    @Transactional(readOnly=true)
    public List<RoleResponse> getRolesByGrupoId( Long grupoId ) {
        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findById( grupoId );
        if ( usuarioGrupoOp.isEmpty() )
            throw new BusinessException( Errors.USER_GROUP_NOT_FOUND );

        UsuarioGrupo usuarioGrupo = usuarioGrupoOp.get();

        List<RoleResponse> roles = new ArrayList<>();
        for( RoleGrupoMap map : usuarioGrupo.getRoles() )
            roles.add( roleMapper.map( map.getRole() ) );

        return roles;
    }

    public void vinculaRole( Long usuarioGrupoId, Long roleId ) {
        Optional<Role> roleOp = roleRepository.findById( roleId );
        if ( roleOp.isEmpty() )
            throw new BusinessException( Errors.ROLE_NOT_FOUND );

        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findById( usuarioGrupoId );
        if ( usuarioGrupoOp.isEmpty() )
            throw new BusinessException( Errors.USER_GROUP_NOT_FOUND );

        RoleGrupoMap map = RoleGrupoMap.builder()
                .role( roleOp.get() )
                .usuarioGrupo( usuarioGrupoOp.get() )
                .build();

        roleGrupoMapRepository.save( map );
    }

    public void removeRoleVinculado( Long usuarioGrupoId, Long roleId ) {
        Optional<RoleGrupoMap> mapOp = roleGrupoMapRepository.get( roleId, usuarioGrupoId );
        if ( mapOp.isEmpty() )
            throw new BusinessException( Errors.LINK_ROLE_USERGROUP_NOT_FOUND);

        Long vinculoId = mapOp.get().getId();

        roleGrupoMapRepository.deleteById( vinculoId );
    }

    public void deleteUsuarioGrupo( Long grupoId ) {
        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findById( grupoId );
        if ( usuarioGrupoOp.isEmpty() )
            throw new BusinessException( Errors.USER_GROUP_NOT_FOUND );

        usuarioGrupoRepository.deleteById( grupoId );
    }

}
