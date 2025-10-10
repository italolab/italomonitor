package com.redemonitor.controller.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequest {

    private String nome;

    private String email;

    private String username;

    private String senha;

}
