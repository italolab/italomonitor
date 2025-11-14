package com.italomonitor.main.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private String nome;

    private String username;

    private String accessToken;
        
    private Long usuarioId;
    
    public Long empresaId;

    public String perfil;

}
