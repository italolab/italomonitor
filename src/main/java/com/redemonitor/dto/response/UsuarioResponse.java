package com.redemonitor.dto.response;

import lombok.*;

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

}
