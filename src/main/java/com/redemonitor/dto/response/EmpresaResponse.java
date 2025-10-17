package com.redemonitor.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmpresaResponse {

    private Long id;
    private String nome;
    private String emailNotif;

}
