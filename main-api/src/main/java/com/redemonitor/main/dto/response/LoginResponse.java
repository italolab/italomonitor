package com.redemonitor.main.dto.response;

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
        
    public Long empresaId;

    public String perfil;

}
