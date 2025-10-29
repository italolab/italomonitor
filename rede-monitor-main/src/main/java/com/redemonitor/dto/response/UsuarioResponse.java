package com.redemonitor.dto.response;

import lombok.*;

import java.util.List;

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

    private EmpresaResponse empresa;

    private List<UsuarioGrupoResponse> grupos;

}
