package com.redemonitor.main.dto.response;

import java.util.List;

import com.redemonitor.main.enums.UsuarioPerfil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private Long id;

    private String nome;

    private String email;

    private String username;

    private UsuarioPerfil perfil;
    
    private EmpresaResponse empresa;

    private List<UsuarioGrupoResponse> grupos;

}
