package com.redemonitor.controller.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private String nome;

    private String username;

    private String token;

}
